package com.zoe.snow.swagger.ctrl;

import com.zoe.snow.test.UserModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * UserController
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/3/21
 */
@Controller
@RestController
@RequestMapping(value = "/snow/users")
@Api
public class UserCtrl {

    static Map<Long, UserModel> users = Collections.synchronizedMap(new HashMap<Long, UserModel>());

    @ApiOperation(value = "getUserList", notes = "get all users")
    @RequestMapping(value = {"/list/get"}, method = RequestMethod.GET)
    public List<UserModel> getUserList() {
        UserModel userModel = new UserModel();
        userModel.setUserName("test");
        userModel.setRemark("just for test");
        List<UserModel> r = new ArrayList<>();
        r.add(userModel);
        return r;
    }
    /*@ApiOperation(value = "getUser", notes = "get user info by user id")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserModel getUser(@PathVariable Long id) {
        return users.get(id);
    }*/
}
