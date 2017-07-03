package com.zoe.snow.service.cxf.user;

import com.zoe.snow.json.Jsonable;
import com.zoe.snow.model.support.BaseModelSupport;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Dept
 *
 * @author Dai Wenqing
 * @date 2016/1/11
 */
@Component("car.user.dept")
public class Dept extends BaseModelSupport {
    private String name;
    private Map<String,Team> map;

    @Jsonable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Jsonable
    public Map<String, Team> getMap() {
        return map;
    }

    public void setMap(Map<String, Team> map) {
        this.map = map;
    }
}
