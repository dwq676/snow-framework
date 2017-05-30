package com.zoe.snow.context.aop;

import com.zoe.snow.Global;
import com.zoe.snow.auth.AuthBean;
import com.zoe.snow.auth.NoNeedVerify;
import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.context.request.Request;
import com.zoe.snow.context.response.Response;
import com.zoe.snow.crud.Result;
import com.zoe.snow.delivery.Http;
import com.zoe.snow.log.Logger;
import com.zoe.snow.message.Message;
import com.zoe.snow.model.annotation.NotNull;
import com.zoe.snow.model.support.user.BaseUserModel;
import com.zoe.snow.model.support.user.UserHelper;
import com.zoe.snow.util.Validator;
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
    private UserHelper userHelper;
    @Autowired
    private AuthBean authBean;
    @Autowired
    private Http http;

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
            if (validator(args, method, nullArgNames)) return result.setResult(null, false, Message.UnAuthorized);

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

    private boolean validator(Object[] args, Method method, List<String> nullArgNames) {
        if (!Validator.isEmpty(method)) {
            List<NotNull> notNulls = new ArrayList<>();
            int pos = 0;
            if (!auth(method, args)) return true;
            for (Parameter parameter : method.getParameters()) {
                NotNull notNull = parameter.getDeclaredAnnotation(NotNull.class);
                if (notNull != null) {
                    notNulls.add(notNull);
                    if (Validator.isEmpty(args[pos]))
                        nullArgNames.add(parameter.getName());
                }
                pos++;
            }
        }
        return false;
    }

    /*private Object rmi(Register register, Class<?> clazz, Method method, Object[] args) {
        if (register == null || clazz == null || method == null)
            return null;
        String url = getUrl(register, clazz, method);
        Object result = null;
        Map<String, String> params = new HashMap<>();
        String[] paramNames = null;
        try {
            paramNames = getParameterNames(clazz.getName(), method.getName());
            int ndx = 0;
            for (String param : paramNames) {
                params.put(param, args[ndx++].toString());
            }
        } catch (Exception e) {
            Logger.error(e, "获取方法参数列表时出现了错误，类名为：[{}]，类名为：[{}]", clazz.getName(), method.getName());
        }
        try {
            POST post = method.getAnnotation(POST.class);
            GET get = method.getAnnotation(GET.class);
            if (get != null) {
                result = http.get(url, null, params);
            } else if (post != null) {
                JSONObject jsonObject = JSONObject.fromObject(params);
                result = http.post(url, params, jsonObject.toString());
            }
            result = TypeConverter.converter(result, method.getReturnType());
        } catch (Exception e) {
            Logger.error(e, "执行远程调用时失败类名为：[{}]，类名为：[{}]", clazz.getName(), method.getName());
        }
        return result;
    }

    private String getUrl(Register register, Class<?> clazz, Method method) {
        StringBuffer urlBuffer = new StringBuffer();
        urlBuffer.append(register.protocol()).append("://");
        urlBuffer.append(register.host());
        urlBuffer.append(":");
        urlBuffer.append(register.port());
        if (!Validator.isEmpty(register.nameSpace())) {
            urlBuffer.append("/");
            urlBuffer.append(register.nameSpace());
        } else {
            urlBuffer.append("/").append(register.prefix());
            Path servicePath = clazz.getAnnotation(Path.class);
            Path methodPath = method.getAnnotation(Path.class);
            if (servicePath != null) {
                if (!servicePath.value().startsWith("/"))
                    urlBuffer.append("/");
                urlBuffer.append(servicePath.value());
            }
            if (methodPath != null) {
                if (!methodPath.value().startsWith("/"))
                    urlBuffer.append("/");
                urlBuffer.append(methodPath.value());
            }
        }
        return urlBuffer.toString();
    }*/


    /**
     * 解析服务请求参数并转换为对应的实体
     */
    private Object[] injectParamOfJson(Method method, Object[] args) {
        /*Request request = BeanFactory.getBean(Request.class);
        if (request != null) {
            int pos = 0;
            for (Parameter parameter : method.getParameters()) {
                Json json = parameter.getDeclaredAnnotation(Json.class);
                if (json != null) {
                    String data = request.get(json.value());
                    if (!Validator.isEmpty(data)) {
                        try {
                            JSONObject jsonObject = JSONObject.fromObject(data);
                            if (jsonObject != null) {
                                //parameter.get
                                //ModelHelper.fromJson(jsonObject,parameter.)
                            }
                        } catch (Exception e) {
                        }
                    }
                }
                pos++;
            }
        }*/

        return args;
    }

    private boolean auth(Method method, Object[] args) {
        if (method == null)
            return false;
        NoNeedVerify noNeedVerify = method.getDeclaringClass().getAnnotation(NoNeedVerify.class);
        if (noNeedVerify == null)
            noNeedVerify = method.getAnnotation(NoNeedVerify.class);
        /*为空证明这个不是特殊方法*/
        if (noNeedVerify == null) {
            if (authBean != null) {
                //打开了授权开关
                if (authBean.getAuthSwitch()) {
                    if (method.getParameters().length == 0)
                        return false;
                    String token = args[method.getParameters().length - 1] == null ?
                            "" : args[method.getParameters().length - 1].toString();
                    /*if (Validator.isEmpty(token))
                        token = BeanFactory.getBean(Request.class).get("token");*/
                    BaseUserModel user = userHelper.getUser(token);
                    if (user == null)
                        return false;
                   /*else {
                        Global.user.set(user);
                        Global.token.set(user.getToken());
                    }*/
                }
            }
        }
        return true;
    }

    @Pointcut("execution(* *..*ServiceImpl.*(..))")
    public void anyMethod() {
    }

    // public void Acc

    public void doThrowing(JoinPoint jp, Throwable ex) {
        Logger.error(ex, "CloseSessionAspect->" + "method " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName() + " throw exception");
    }
}
