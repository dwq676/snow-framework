package com.zoe.snow.context.aop;

import com.alibaba.fastjson.JSONObject;
import com.zoe.snow.Global;
import com.zoe.snow.auth.*;
import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.conf.AuthenticationConf;
import com.zoe.snow.conf.Configuration;
import com.zoe.snow.context.request.Request;
import com.zoe.snow.context.response.Response;
import com.zoe.snow.crud.Result;
import com.zoe.snow.log.Logger;
import com.zoe.snow.message.Message;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.ResultSet;
import com.zoe.snow.model.annotation.NotNull;
import com.zoe.snow.model.mapper.ModelTable;
import com.zoe.snow.model.mapper.ModelTables;
import com.zoe.snow.util.Validator;
import com.zoe.snow.model.Validatable;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * 所有服务端方法访问拦截器
 * 1、用于参数检验，参数不为空的检测，根据自定义注释进行扩展
 * 2、对于服务调用判断是否为远程调用，并按策略进行执行
 * 3、访问权限的控制
 *
 * @author Dai Wenqing
 * @date 2016/3/17
 */
@Aspect
@Service
public class AccessAspect {
    @Value("${snow.request.channel:local}")
    protected String requestChannel;
    @Autowired(required = false)
    private Set<com.zoe.snow.dao.Closable> cloneableSet;
    @Autowired
    private Request request;

    @After("anyMethod()")
    public void doAfter(JoinPoint jp) {
        // 能执行Jp切面，即保证了jp.getSignature().getDeclaringTypeName()不会出现异常
        if (!jp.getSignature().getDeclaringTypeName().endsWith("CrudService")) {
            if (cloneableSet != null) {
                cloneableSet.forEach(com.zoe.snow.dao.Closable::close);
            }
        }
    }

    @Before("anyMethod()")
    public void doBefore(JoinPoint jp) {

    }

    private Object authentication(Result result) {
        return null;
    }

    @Around("anyMethod()")
    public Object doAround(ProceedingJoinPoint pj) {
        Object[] args = pj.getArgs();
        Result result = BeanFactory.getBean(Result.class);
        Method method = null;
        Object r = null;
        try {
            method = AopUtil.getMethod(pj);
            List<String> nullArgNames = new ArrayList<>();
            String token = args[method.getParameters().length - 1] == null ?
                    "" : args[method.getParameters().length - 1].toString();
            if (Validator.isEmpty(token))
                return result.setResult(null, false, Message.InvalidToken);

            Result validatorResult = validator(args, token, method, nullArgNames);
            if (!validatorResult.isSuccess()) return validatorResult;

            if (nullArgNames.size() > 0) {
                return result.setResult(null, false, Message.ParameterPositionInHolderIsnull, String.join(",", nullArgNames),
                        pj.getSignature().toShortString());
            }
        } catch (Exception e) {

        }
        try {
            /*Register register = pj.getTarget().getClass().getAnnotation(Register.class);
            if (method == null)
                return null;
            Produces produces = method.getClass().getAnnotation(Produces.class);
            if (requestChannel.endsWith("local") && register == null && produces == null) {
                return pj.proceed(args);
            } else if (requestChannel.endsWith("remote") || register != null) {
                rmi(register, pj.getTarget().getClass(), method, args);
            }*/
            r = pj.proceed(args);
        } catch (Throwable e) {
            Response response = BeanFactory.getBean(Response.class);
            response.setStatusCode(500);
            Logger.error(e, "执行服务端方法出现了错误！");
        } finally {
            return AopUtil.getResult(pj, r);
        }
    }

    private Result validator(Object[] args, String token, Method method, List<String> nullArgNames) {
        return Result.reply(() -> {
            if (!Validator.isEmpty(method)) {
                List<NotNull> notNulls = new ArrayList<>();
                int pos = 0;
                //验证token有效性
                AuthenticationConf conf = BeanFactory.getBean(AuthenticationConf.class);
                Authentication authentication = null;
                ResultSet result = null;
                Result authResult = auth(method, token);
                //验证权限
                if (authResult.isSuccess()) {
                    for (Parameter parameter : method.getParameters()) {
                        NotNull notNull = parameter.getDeclaredAnnotation(NotNull.class);
                        try {
                            Class<?> classZ = (Class<?>) parameter.getParameterizedType();
                            if (args[pos] instanceof Validatable) {
                                ModelTables modelTables = BeanFactory.getBean(ModelTables.class);
                                ModelTable modelTable = modelTables.get((Class<? extends Model>) classZ);

                            }
                        } catch (Exception e) {

                        }

                        if (notNull != null) {
                            notNulls.add(notNull);
                            if (Validator.isEmpty(args[pos]))
                                nullArgNames.add(parameter.getName());
                        }
                        pos++;
                    }
                } else if (Message.UnAuthorized.getType().contains(authResult.getCode()))
                    return Message.UnAuthorized;
                else
                    return Message.InvalidToken;
            }
            return Message.Success;
        });
    }

    private Result auth(Method method, String token) {
        return Result.reply(() -> {
            if (method == null)
                return false;
            NoNeedVerify noNeedVerify = method.getDeclaringClass().getAnnotation(NoNeedVerify.class);
            NeedAdmin needAdmin = method.getDeclaringClass().getAnnotation(NeedAdmin.class);

            if (noNeedVerify == null)
                noNeedVerify = method.getAnnotation(NoNeedVerify.class);
            if (needAdmin == null)
                needAdmin = method.getAnnotation(NeedAdmin.class);

            if (noNeedVerify != null && noNeedVerify.NoNeedEffectiveness())
                return true;

            AuthenticationConf conf = BeanFactory.getBean(AuthenticationConf.class);
            Authentication authentication = null;
            ResultSet result = null;
            if (method.getParameters().length == 0)
                return false;
            String remoteAuthBeanName = "snow.auth.service.remote";
            PermissionBean permissionBean = new PermissionBean();
            permissionBean.setMethod(request.getMethod());
            String url = Validator.isEmpty(request.get("CONTROL_URL")) ? request.getUri() : request.get("CONTROL_URL");
            permissionBean.setUrl(url);
            permissionBean.setType(((Configuration) conf).getProjectName());
            boolean loginResult = false;

            //验证token
            if (conf.getAuthIsThirdPart()) {
                authentication = BeanFactory.getBean(remoteAuthBeanName);
                String re = authentication.verify(token).toString();
                if (re.trim().startsWith("{")) {
                    JSONObject jsonObject = JSONObject.parseObject(re);
                    loginResult = jsonObject.getBoolean("success");
                    if (!loginResult)
                        return Message.InvalidToken;
                }
                //isRemote = true;
            } else {
                authentication = (Authentication) BeanFactory.getBean(Local.class);
                result = ResultSet.class.cast(authentication.verify(token));
                loginResult = result.isSuccess();
                if (!loginResult)
                    return Message.InvalidToken;
            }

            //需要验证权限
            if (noNeedVerify == null) {
                if (conf.getAuthIsThirdPart()) {
                    authentication = BeanFactory.getBean(remoteAuthBeanName);
                    String re = authentication.verify(token).toString();
                    if (re.trim().startsWith("{")) {
                        JSONObject jsonObject = JSONObject.parseObject(re);
                        boolean isRoot = jsonObject.getJSONObject("data").getBoolean(Global.Constants.auth.IS_SUPER_ADMIN);
                        if (!isRoot) {
                            jsonObject = JSONObject.parseObject(authentication.verify(permissionBean, token).toString());
                            loginResult = jsonObject.getBoolean("success");
                        }
                        if (needAdmin != null)
                            loginResult = isRoot;
                    }
                    //isRemote = true;
                } else {
                    authentication = (Authentication) BeanFactory.getBean(Local.class);
                    result = ResultSet.class.cast(authentication.verify(token));
                    loginResult = result.isSuccess();
                    JSONObject jo = (JSONObject) JSONObject.toJSON(result.getData());
                    boolean isRoot = false;
                    if (jo != null)
                        isRoot = jo.getBoolean(Global.Constants.auth.IS_SUPER_ADMIN);
                    if (!isRoot) {
                        ResultSet permissionRe = ResultSet.class.cast(authentication.verify(permissionBean, token));
                        loginResult = permissionRe.isSuccess();
                    }
                    if (needAdmin != null)
                        loginResult = isRoot;
                }
                return loginResult ? loginResult : Message.UnAuthorized;
            } else
                return true;
        });
    }

    @Pointcut("execution(* *..*ServiceImpl.*(..))")
    public void anyMethod() {
    }

    // public void Acc

    public void doThrowing(JoinPoint jp, Throwable ex) {
        Logger.error(ex, "CloseSessionAspect->" + "method " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName() + " throw exception");
    }
}
