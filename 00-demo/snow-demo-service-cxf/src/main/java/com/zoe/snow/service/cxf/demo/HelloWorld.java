package com.zoe.snow.service.cxf.demo;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * com.zoe.snow.service.cxf.HelloWorld
 *
 * @author Dai Wenqing
 * @date 2015/12/29
 */
@WebService
public interface HelloWorld {
    String sayHi(@WebParam(name = "text") String text);
}