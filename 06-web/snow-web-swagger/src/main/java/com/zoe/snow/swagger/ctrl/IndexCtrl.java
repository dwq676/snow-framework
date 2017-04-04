package com.zoe.snow.swagger.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * IndexCtrl
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/3/27
 */
@Controller
@RestController
@RequestMapping(value = "/")
public class IndexCtrl {
    @RequestMapping("/docs")
    public ModelAndView index() {
        return new ModelAndView("redirect:/swagger-ui.html");
    }

}
