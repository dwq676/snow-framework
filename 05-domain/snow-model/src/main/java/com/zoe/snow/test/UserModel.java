package com.zoe.snow.test;

import javax.persistence.*;

import com.zoe.snow.model.annotation.Datasource;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zoe.snow.model.annotation.Jsonable;
import com.zoe.snow.model.support.BaseModelSupport;

/**
 * Created by Administrator on 2016/3/15.
 */
@Entity
@Table(name = "crip_user")
@Component("crip.user.model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Datasource("default")
public class UserModel extends BaseModelSupport {

    private String userName;
    private String password;
    private School school;

    /*private String sex;

    @Column(name = "sex")
    @Jsonable
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }*/

    @Column(name = "user_name")
    @Jsonable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "password")
    @Jsonable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Jsonable
    @JoinColumn(name = "school")
    @OneToOne(fetch = FetchType.LAZY)
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
