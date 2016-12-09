package com.zoe.snow.context.aop;

import com.zoe.snow.Global;
import com.zoe.snow.auth.AuthBean;
import com.zoe.snow.auth.NoNeedVerify;
import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.crud.Result;
import com.zoe.snow.log.Logger;
import com.zoe.snow.message.Message;
import com.zoe.snow.model.annotation.NotNull;
import com.zoe.snow.model.support.user.BaseUserModelSupport;
import com.zoe.snow.model.support.user.UserHelper;
import com.zoe.snow.util.Validator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired(required = false)
    private Set<com.zoe.snow.dao.Closable> cloneableSet;
    @Autowired
    private UserHelper userHelper;
    @Autowired
    private AuthBean authBean;

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
                NoNeedVerify noNeedVerify = method.getAnnotation(NoNeedVerify.class);
                /*为空证明这个不是特殊方法*/
                if (noNeedVerify == null) {
                    if (authBean != null) {
                        //打开了授权开关
                        if (authBean.getAuthSwitch()) {
                            if (method.getParameters().length == 0)
                                return result.setResult(null, Message.UnAuthorized);
                            String token = args[method.getParameters().length - 1].toString();
                            BaseUserModelSupport user = userHelper.getUser(token);
                            if (user == null)
                                return result.setResult(null, Message.UnAuthorized);
                            else {
                                Global.user.set(user);
                                Global.token.set(user.getToken());
                            }
                        }
                    }
                }
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
                //return result;
            }
        } catch (Exception e) {

        }
        try {
            return proceedingJoinPoint.proceed(args);

        } catch (Throwable e) {
            Logger.error(e, e.getMessage());
            result.setResult(null, Message.ServiceError);
        }
        return result;
    }

    @Pointcut("execution(* *..*ServiceImpl.*(..))")
    public void anyMethod() {
    }

    // public void Acc

    public void doThrowing(JoinPoint jp, Throwable ex) {
        Logger.error(ex, "CloseSessionAspect->" + "method " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName() + " throw exception");
    }
}
