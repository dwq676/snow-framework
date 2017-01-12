package com.zoe.snow.context.aop;

import com.zoe.snow.Global;
import com.zoe.snow.auth.AuthBean;
import com.zoe.snow.auth.NoNeedVerify;
import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.context.request.Request;
import com.zoe.snow.crud.Result;
import com.zoe.snow.delivery.Http;
import com.zoe.snow.delivery.Register;
import com.zoe.snow.delivery.Verb;
import com.zoe.snow.log.Logger;
import com.zoe.snow.message.Message;
import com.zoe.snow.model.annotation.NotNull;
import com.zoe.snow.model.support.user.BaseUserModelSupport;
import com.zoe.snow.model.support.user.UserHelper;
import com.zoe.snow.util.Validator;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import net.sf.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.Path;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * CloseSessionAspect
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
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) {
        Object[] args = proceedingJoinPoint.getArgs();
        Result result = BeanFactory.getBean(Result.class);
        try {
            Method method = AopUtil.getMethod(proceedingJoinPoint);
            List<String> nullArgNames = new ArrayList<>();
            if (!Validator.isEmpty(method)) {
                List<NotNull> notNulls = new ArrayList<>();
                int pos = 0;
                if (!auth(method, args)) return result.setResult(null, Message.UnAuthorized);
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

            if (nullArgNames.size() > 0) {
                return result.setResult(null, Message.ParameterPositionInHolderIsnull, String.join(",", nullArgNames),
                        proceedingJoinPoint.getSignature().toShortString());
            }
        } catch (Exception e) {

        }
        try {
            Register register = proceedingJoinPoint.getTarget().getClass().getAnnotation(Register.class);
            if (requestChannel.endsWith("local") && register == null) {
                return proceedingJoinPoint.proceed(args);
            } else if (requestChannel.endsWith("remote")) {

            }
        } catch (Throwable e) {
            Logger.error(e, e.getMessage());
            result.setResult(null, Message.ServiceError);
        }
        return result;
    }

    private String getParameterName(String clazzName, String methodName, int ndx)
            throws NotFoundException {
        String[] paramNames = getParameterNames(clazzName, methodName);
        if (paramNames.length >= ndx)
            return paramNames[ndx];
        return "";
    }

    private String[] getParameterNames(String clazzName, String methodName) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(this.getClass());
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                .getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            // exception
        }
        String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramNames.length; i++)
            paramNames[i] = attr.variableName(i + pos);
        return paramNames;
    }

    private Object rmi(Register register, Class<?> clazz, Method method, Object[] args) {
        if (register == null || clazz == null || method == null)
            return null;
        String url = getUrl(register, clazz, method);
        //urlBuffer.append(register.)
        if (register.verb() == Verb.GET) {

        }
        return null;
    }

    private String getUrl(Register register, Class<?> clazz, Method method) {
        StringBuffer urlBuffer = new StringBuffer();
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
            if (!servicePath.value().startsWith("/"))
                urlBuffer.append("/");
            urlBuffer.append(servicePath.value());
            if (!methodPath.value().startsWith("/"))
                urlBuffer.append("/");
            urlBuffer.append(methodPath.value());
        }
        return urlBuffer.toString();
    }


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
        NoNeedVerify noNeedVerify = method.getAnnotation(NoNeedVerify.class);
        /*为空证明这个不是特殊方法*/
        if (noNeedVerify == null) {
            if (authBean != null) {
                //打开了授权开关
                if (authBean.getAuthSwitch()) {
                    if (method.getParameters().length == 0)
                        return false;
                    String token = args[method.getParameters().length - 1].toString();
                    if (Validator.isEmpty(token))
                        token = BeanFactory.getBean(Request.class).get("token");
                    BaseUserModelSupport user = userHelper.getUser(token);
                    if (user == null)
                        return false;
                    else {
                        Global.user.set(user);
                        Global.token.set(user.getToken());
                    }
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
