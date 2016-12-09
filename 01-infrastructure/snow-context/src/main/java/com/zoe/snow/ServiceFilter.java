package com.zoe.snow;

import java.io.File;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.conf.Configuration;
import com.zoe.snow.context.Context;
import com.zoe.snow.context.request.HttpServletRequestAware;
import com.zoe.snow.context.request.Request;
import com.zoe.snow.context.response.HttpServletResponseAware;
import com.zoe.snow.context.response.Response;
import com.zoe.snow.context.session.HttpSessionAware;
import com.zoe.snow.context.session.Session;
import com.zoe.snow.context.session.SessionAdapter;
import com.zoe.snow.context.session.SessionAdapterAware;
import com.zoe.snow.log.Logger;
import com.zoe.snow.util.Converter;

/**
 * ServiceFilter
 *
 * @author Dai Wenqing
 * @date 2016/1/19
 */
public class ServiceFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // this.context.setRoot(filterConfig.getServletContext().getRealPath(""));
        setPath(filterConfig.getServletContext().getRealPath(""), filterConfig.getServletContext().getContextPath());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        setContext((HttpServletRequest) request, (HttpServletResponse) response);
        if (!ignore((HttpServletRequest) request, (HttpServletResponse) response))
            chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    /**
     * 忽略文件未日期未改的文件，减少重复请求
     * 
     * @param request
     * @param response
     */
    public boolean ignore(HttpServletRequest request, HttpServletResponse response) {
        Context context = BeanFactory.getBean(Context.class);
        String uri = request.getRequestURI();

        File file = new File(context.getAbsolutePath(uri));
        /*if (!file.exists())
            return false;*/

        long time = request.getDateHeader("If-Modified-Since");
        if (time > 0) {
            if (time >= file.lastModified()) {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                if (Logger.isDebugEnable())
                    Logger.debug("请求的资源[{}]已是最新的，返回[304]状态。", uri);
                return true;
            }
        }
        response.addDateHeader("Last-Modified", file.lastModified());
        response.setHeader("ETag", Converter.toString(file.lastModified()));
        /*if (Logger.isDebugEnable())
            Logger.debug("请求资源[{}]已变更，重新获取最新资源。", uri);*/
        return false;
    }

    private void setPath(String real, String contextPath) {
        Context context = BeanFactory.getBean(Context.class);
        context.setPath(real, contextPath);
        if (Logger.isInfoEnable())
            Logger.info("部署项目路径[{}]。", context);
    }

    /**
     * 设置上下文
     * 
     * @param request
     * @param response
     */
    private void setContext(HttpServletRequest request, HttpServletResponse response) {
        Request r = BeanFactory.getBean(Request.class);
        ((HttpServletRequestAware) r).setHttpServletRequest(request);

        Configuration configuration = BeanFactory.getBean(Configuration.class);
        SessionAdapter sessionAdapter = BeanFactory.getBean("snow.context.session." + configuration.getSessionName());
        ((HttpSessionAware) sessionAdapter).set(request.getSession());

        Session session = BeanFactory.getBean(Session.class);
        ((SessionAdapterAware) session).setSession(sessionAdapter);

        Response rp = BeanFactory.getBean(Response.class);
        ((HttpServletResponseAware) rp).set(response);
    }
}
