package com.zoe.snow.web;

import com.zoe.snow.context.request.Request;
import com.zoe.snow.util.Security;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * IndexCtrl
 *
 * @author Dai Wenqing
 * @date 2016/8/4
 */
@Controller("snow.web.index")
public class IndexCtrl {

    @Autowired
    private Request request;

    @RequestMapping("/")
    public ModelAndView getIndexView() {
        return new ModelAndView("/module/index/index");
    }
}
