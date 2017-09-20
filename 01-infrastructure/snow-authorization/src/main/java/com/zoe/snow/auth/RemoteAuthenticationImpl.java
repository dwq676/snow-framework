package com.zoe.snow.auth;

import com.zoe.snow.conf.AuthenticationConf;
import com.zoe.snow.delivery.Http;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return http.post(getPath() + "login", null, JSONObject.fromObject(accountViewModel).toString(), null);
    }

    @Override
    @NoNeedVerify
    public Object logout(String token) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        return http.post(getPath() + "logout", null, jsonObject.toString(), null);
    }

    @Override
    @NoNeedVerify
    public Object verify(String token) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);
        return http.post(getPath() + "verify", null, jsonObject.toString(), null);
    }

    private String getPath() {
        String pro = HTTP_PROTOCOL;
        if (authenticationConf.getAuthProtocol() == "https")
            pro = HTTPS_PROTOCOL;
        StringBuffer stringBuffer = new StringBuffer().append(pro).append(authenticationConf.getAuthHost());
        if (authenticationConf.getAuthPort() != 80)
            stringBuffer.append(":").append(authenticationConf.getAuthPort());
        stringBuffer.append("/").append(authenticationConf.getAuthProject()).append("/");
        return stringBuffer.toString();
    }
}
