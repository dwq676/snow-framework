package com.zoe.snow.web;

import com.zoe.snow.log.Logger;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ExceptionHandler
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/5/2
 */
@Controller("snow.web.exception")
public class ExceptionHandler extends SimpleMappingExceptionResolver {
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView modelAndView = super.doResolveException(request, response, handler, ex);
        String url = WebUtils.getPathWithinApplication(request);
        //logger.error("controller error.url=" + url, ex);
        Logger.error(ex, "service error");
        if (modelAndView == null) {
            modelAndView = new ModelAndView("/error/error");
        }
        return modelAndView;
    }
}
