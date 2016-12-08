package com.zoe.snow.test;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserCtrl
 *
 * @author Dai Wenqing
 * @date 2016/11/18
 */
@RestController
@RequestMapping("/test/user")
public class TestUserCtrl {
    @RequestMapping("/{id}")
    public String view(@PathVariable("id") String id) {
        return id;
    }
}

