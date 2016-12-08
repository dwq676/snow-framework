package com.zoe.snow.service.cxf.demo;

import javax.jws.WebService;

/**
 * com.zoe.snow.service.cxf.HelloWorldImpl
 *
 * @author Dai Wenqing
 * @date 2015/12/29
 */
@WebService(endpointInterface= "com.zoe.snow.service.cxf.demo.HelloWorld",serviceName="com.zoe.snow.service.cxf.HelloWorld")
public class HelloWorldImpl implements HelloWorld {
    @Override
    public String sayHi(String text) {
        //D:\code\carzoe\trunk\zoe-car-web\target\work\webapp\
        //D:/code/snow/trunk/03-services/snow-service-cxf/target/work/webapp/WEB-INF/classes/
        return "Hello " + text;
    }

}
