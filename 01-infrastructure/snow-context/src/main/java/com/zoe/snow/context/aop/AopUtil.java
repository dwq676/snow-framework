package com.zoe.snow.context.aop;

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
}
