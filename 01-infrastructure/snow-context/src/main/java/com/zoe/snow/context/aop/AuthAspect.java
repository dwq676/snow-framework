package com.zoe.snow.context.aop;

import com.zoe.snow.log.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Service;

/**
 * AuthAspect
 *
 * @author Dai Wenqing
 * @date 2016/2/26
 */
@Aspect
@Service
public class AuthAspect {
/*    public void doAfter(JoinPoint jp) {
        System.out.println("log Ending method: " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());
    }

    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long time = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        time = System.currentTimeMillis() - time;
        System.out.println("process time: " + time + " ms");
        return retVal;
    }*/

    public void doBefore(JoinPoint jp) {

    }

    public void doThrowing(JoinPoint jp, Throwable ex) {
        Logger.error(ex, "AuthAspect->" + "method " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName()
                + " throw exception");
    }
}
