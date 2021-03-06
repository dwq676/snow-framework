package com.zoe.snow.test;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;
import javax.annotation.Resource;

/**
 * TestService
 *
 * @author Dai Wenqing
 * @date 2016/12/8
 */
@Path("/resteasy")
@Service("snow.service.resteasy.test")
public class TestServiceImpl {

    @GET
    @Path(value = "user/{msg}")
    @Produces("application/json")
    @Transactional
    public String getMessage(@PathParam("msg") String msg) {
        return "[Hello dear! ]" + msg;
    }
}
