package com.zoe.snow.context.response;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.zoe.snow.log.Logger;
import org.springframework.stereotype.Component;

/**
 * ResponseImpl
 *
 * @author Dai Wenqing
 * @date 2016/1/27
 */
@Component("snow.context.response")
public class ResponseImpl implements Response, HttpServletResponseAware {
    protected String servletContextPath;
    protected ThreadLocal<HttpServletResponse> responseThreadLocal = new ThreadLocal<>();

    @Override
    public void setContentType(String contentType) {
        responseThreadLocal.get().setContentType(contentType);
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return responseThreadLocal.get().getOutputStream();
    }

    @Override
    public void redirectTo(String url) {
        try {
            responseThreadLocal.get().sendRedirect(url);
        } catch (IOException e) {
            Logger.warn(e, "跳转的URL[{}]地址时发生异常！", url);
        }
    }

    @Override
    public void set(HttpServletResponse httpServletResponse) {
        this.responseThreadLocal.set(httpServletResponse);
    }
}
