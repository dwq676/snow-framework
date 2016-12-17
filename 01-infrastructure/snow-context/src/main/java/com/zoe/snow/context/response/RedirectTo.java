package com.zoe.snow.context.response;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * RedirectTo
 *
 * @author Dai Wenqing
 * @date 2016/11/10
 */
@Controller("snow.web.redirect-to")
public class RedirectTo {
    private String url;
    private List<String> excludeUrlList = new ArrayList<>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getExcludeUrlList() {
        return excludeUrlList;
    }

    public void setExcludeUrlList(List<String> excludeList) {
        this.excludeUrlList = excludeList;
    }
}
