package com.zoe.snow.context.aop;

import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.context.aop.annotation.LogTo;
import com.zoe.snow.context.request.Request;
import com.zoe.snow.log.BasicLogType;
import com.zoe.snow.model.support.user.UserHelper;
import net.sf.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import com.zoe.snow.log.Logger;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * LoggerAspect
 *
 * @author Dai Wenqing
 * @date 2016/2/26
 */
@Aspect
@Service
public class LoggerAspect {

    @Pointcut("@annotation(com.zoe.snow.context.aop.annotation.LogTo)")
    public void anyMethod() {
    }

    @Before("anyMethod()")
    public void doBefore(JoinPoint jp) {
        //logging(jp);
    }

    private void logging(JoinPoint jp) {
        Method method = AopUtil.getMethod(jp);
        if (method != null) {
            LogTo logTo = method.getAnnotation(LogTo.class);
            if (logTo != null) {
                JSONObject jsonObject = new JSONObject();
                UserHelper userHelper = BeanFactory.getBean(UserHelper.class);
                Request request = BeanFactory.getBean(Request.class);
                jsonObject.put("user_name", userHelper.getUsername());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                jsonObject.put("datetime", sdf.format(new Date()));
                jsonObject.put("ip", request.getIp());
                jsonObject.put(BasicLogType.InContent.getType(), logTo.value());
                jsonObject.put("target",jp.getTarget().getClass().getName());
                jsonObject.put("method",jp.getSignature().getName());
                Logger.info(BasicLogType.InContent, jsonObject.toString());
            }
        }
    }

    public void doThrowing(JoinPoint jp, Throwable ex) {
        Logger.error(ex, "LoggerAspect->" + "method " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName()
                + " throw exception");
    }
}
