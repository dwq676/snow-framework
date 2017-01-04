package com.zoe.snow.test;

import com.zoe.snow.model.annotation.Jsonable;
import com.zoe.snow.model.support.BaseModelSupport;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Student
 *
 * @author Dai Wenqing
 * @date 2016/9/5
 */
public class Student extends BaseModelSupport {
    private String name;
    private School school;

    @Column(name = "name")
    @Jsonable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Jsonable
    @JoinColumn(name = "school_id")
    @OneToOne(fetch = FetchType.LAZY)
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
