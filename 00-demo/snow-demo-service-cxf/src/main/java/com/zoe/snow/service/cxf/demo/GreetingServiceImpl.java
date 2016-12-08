package com.zoe.snow.service.cxf.demo;

/**
 * GreetingServiceImpl
 *
 * @author Dai Wenqing
 * @date 2015/12/29
 */

import javax.jws.WebService;
import java.util.Calendar;

@WebService(endpointInterface = "com.zoe.snow.service.cxf.demo.Greeting")
public class GreetingServiceImpl implements Greeting {

    public String greeting(String userName) {
        return "Hello " + userName + ", currentTime is "
                + Calendar.getInstance().getTime();
    }
}
