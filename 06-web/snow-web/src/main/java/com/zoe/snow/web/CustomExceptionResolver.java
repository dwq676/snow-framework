package com.zoe.snow.web;

import com.zoe.snow.log.Logger;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CustomExceptionResolver
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/5/2
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {

        String url = WebUtils.getPathWithinApplication(request);
        //logger.error("controller error.url=" + url, ex);
        Logger.error(ex, "service error");
        return new ModelAndView("/error/error");

    }
}