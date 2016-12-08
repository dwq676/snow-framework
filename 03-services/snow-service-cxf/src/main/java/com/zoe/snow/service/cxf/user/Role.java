package com.zoe.snow.service.cxf.user;

import com.zoe.snow.model.annotation.Jsonable;
import com.zoe.snow.model.support.BaseModelSupport;
import org.springframework.stereotype.Component;

/**
 * role
 *
 * @author Dai Wenqing
 * @date 2016/1/8
 */
@Component("car.user.role")
public class Role extends BaseModelSupport {
    private  String name;
    private  Dept dept;

    @Jsonable
    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @Jsonable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
