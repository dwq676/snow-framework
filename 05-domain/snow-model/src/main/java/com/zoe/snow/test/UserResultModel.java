package com.zoe.snow.test;

import com.zoe.snow.model.RawModel;

/**
 * UserResultModel
 *
 * @author Dai Wenqing
 * @date 2016/3/29
 */
public class UserResultModel extends RawModel {
    private String userName;


/*
    public UserResultModel(String id, String userName) {
        this.setId(id);
        this.userName = userName;
    }
*/

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
