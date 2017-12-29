package com.zoe.snow.auth;

import com.zoe.snow.Global;
import com.zoe.snow.conf.AuthenticationConf;
import com.zoe.snow.conf.CoreConfig;
import com.zoe.snow.delivery.Http;
import com.zoe.snow.util.Validator;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * RemoteAuthenticationImpl
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/6/11
 */
@Service("snow.auth.service.remote")
public class RemoteAuthenticationImpl implements Authentication, Remote {
    private static final String HTTP_PROTOCOL = "http://";
    private static final String HTTPS_PROTOCOL = "https://";

    @Autowired
    private AuthenticationConf authenticationConf;
    @Autowired
    private Http http;

    @Override
    @NoNeedVerify
    public Object login(String ip, AccountViewModel accountViewModel) {
        Map<String, String> header = new HashMap<>();
        header.putIfAbsent("content-type", "application/json");
        return http.post(getPath() + "login", header, JSONObject.fromObject(accountViewModel).toString(), null);
    }

    @Override
    @NoNeedVerify
    public Object logout(String token) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        Map<String, String> header = new HashMap<>();
        header.putIfAbsent("content-type", "application/json");
        return http.post(getPath() + "logout", header, jsonObject.toString(), null);
    }

    @Override
    @NoNeedVerify
    public Object verify(String token) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        Map<String, String> header = new HashMap<>();
        header.putIfAbsent("content-type", "application/json");
        return http.post(getPath() + "verify", header, jsonObject.toString(), null);
    }

    @Override
    public Object verify(PermissionBean permissionBean, String token) {
        JSONObject jsonObject = JSONObject.fromObject(permissionBean);
        //jsonObject.put("permission", permissionBean);
        Map<String, String> header = new HashMap<>();
        header.putIfAbsent("content-type", "application/json");
        header.putIfAbsent("Authorization", "Token " + token);
        return http.post(getPath() + "/permission/verify", header, jsonObject.toString(), null);
    }

    private String getPath() {
        String pro = HTTP_PROTOCOL;
        if (authenticationConf.getAuthProtocol() == "https")
            pro = HTTPS_PROTOCOL;
        String host = authenticationConf.getAuthHost();
        if (Validator.isEmpty(host))
            host = Global.GLOBAL_DOMAIN;
        StringBuffer stringBuffer = new StringBuffer().append(pro).append(host);
        if (authenticationConf.getAuthPort() != 80)
            stringBuffer.append(":").append(authenticationConf.getAuthPort());
        stringBuffer.append("/").append(authenticationConf.getAuthProject()).append("/");
        return stringBuffer.toString();
    }
}
