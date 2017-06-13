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
    @Autowired
    private AuthenticationConf authenticationConf;
    @Autowired
    private Http http;

    @Override
    public Object login(String appId, String ip, AccountViewModel accountViewModel) {
        return http.post(getPath() + "login", null, JSONObject.fromObject(accountViewModel).toString(), null);
    }

    @Override
    public Object logout(String token) {
        /*JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);*/
        return http.post(getPath() + "logout", null, token, null);
    }

    @Override
    public Object verify(String token) {
        /*JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);*/
        return http.post(getPath() + "verify", null, token, null);
    }

    private String getPath() {
        return new StringBuffer().append(HTTP_PROTOCOL).append(authenticationConf.getAuthHost())
                .append(":").append(authenticationConf.getAuthPort())
                .append("/").append(authenticationConf.getAuthProject()).append("/")
                .toString();
    }
}
