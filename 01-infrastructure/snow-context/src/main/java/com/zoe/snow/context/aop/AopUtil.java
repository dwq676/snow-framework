package com.zoe.snow.context.aop;

import com.zoe.snow.crud.Result;
import com.zoe.snow.message.Message;
import com.zoe.snow.model.TypeConverter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * AopUtil
 *
 * @author Dai Wenqing
 * @date 2016/10/31
 */
public class AopUtil {
    public static Method getMethod(JoinPoint joinPoint) {
        Method method = null;
        for (Method m : joinPoint.getTarget().getClass().getDeclaredMethods()) {
            if (m.getName().equals(joinPoint.getSignature().getName())) {
                if (m.getParameters().length == joinPoint.getArgs().length) {
                    method = m;
                    break;
                }
            }
        }
        return method;
    }

    public static Object getResult(ProceedingJoinPoint proceedingJoinPoint, Object r) {
        Class<?> resultType = AopUtil.getMethod(proceedingJoinPoint).getReturnType();
        if (r == null) {
            if (resultType.equals(Result.class)) {
                Result result = Result.class.cast(r);
                result.setResult(null, false, Message.ServiceError);
            } else
                r = TypeConverter.converter(r, resultType);
        }
        return r;
    }
}
