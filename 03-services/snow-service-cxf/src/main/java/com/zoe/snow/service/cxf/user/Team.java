package com.zoe.snow.service.cxf.user;

import com.zoe.snow.json.Jsonable;
import com.zoe.snow.model.support.BaseModelSupport;

/**
 * Team
 *
 * @author Dai Wenqing
 * @date 2016/1/11
 */
public class Team extends BaseModelSupport {
    private  String code;

    @Jsonable
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
