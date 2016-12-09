package com.zoe.snow.context.response;

import org.springframework.stereotype.Controller;

/**
 * RedirectTo
 *
 * @author Dai Wenqing
 * @date 2016/11/10
 */
@Controller("snow.web.redirect-to")
public class RedirectTo {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
