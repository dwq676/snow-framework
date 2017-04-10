package com.zoe.snow.swagger.ctrl;

import com.zoe.snow.auth.NoNeedVerify;
import com.zoe.snow.crud.CrudService;
import com.zoe.snow.crud.service.proxy.HBaseProxy;
import com.zoe.snow.test.UserModel;
import com.zoe.snow.util.Generator;
import com.zoe.snow.util.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping(value = "/api")
@Api
public class UserCtrl {

    static Map<Long, UserModel> users = Collections.synchronizedMap(new HashMap<Long, UserModel>());
    private static String MPING_DATA_TABLE = "rdc_rawdata_mping";
    /*@ApiOperation(value = "getUser", notes = "get user info by user id")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserModel getUser(@PathVariable Long id) {
        return users.get(id);
    }*/
    @Autowired
    private CrudService crudService;

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

    @RequestMapping(value = "/mping/{row_key:.+}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "get mping raw data", notes = "get mping raw data")
    public JSONObject getMPingRawData(@PathVariable("row_key") String rowKey) {
        //if(!Validator)
        if (Validator.isEmpty(rowKey))
            return new JSONObject();
        String[] rowKeys = rowKey.split(",");
        HBaseProxy hBaseProxy = crudService.hbase().select("dat").from(MPING_DATA_TABLE);
        for (String key : rowKeys) {
            hBaseProxy.where(Generator.uuid(), key);
        }
        return hBaseProxy.asJsonObject();

    }
}
