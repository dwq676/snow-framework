package com.zoe.snow.context.response;

import javax.servlet.http.HttpServletResponse;

/**
 * HttpServletResponseAware
 *
 * @author Dai Wenqing
 * @date 2016/1/27
 */
public interface HttpServletResponseAware {
    void set(HttpServletResponse httpServletResponse);
}
