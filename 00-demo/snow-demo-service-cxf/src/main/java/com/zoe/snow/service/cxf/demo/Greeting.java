package com.zoe.snow.service.cxf.demo;

import javax.jws.WebService;

/**
 * Greeting
 *
 * @author Dai Wenqing
 * @date 2015/12/29
 */
@WebService
public interface Greeting {
    public String greeting(String userName);
}