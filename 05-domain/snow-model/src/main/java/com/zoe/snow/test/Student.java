package com.zoe.snow.test;

import com.zoe.snow.json.Jsonable;
import com.zoe.snow.model.support.BaseModelSupport;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Student
 *
 * @author Dai Wenqing
 * @date 2016/9/5
 */
@Entity
@Table(name = "crip_student")
@Component("crip.user.student")
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
