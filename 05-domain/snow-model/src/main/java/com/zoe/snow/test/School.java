package com.zoe.snow.test;

import com.zoe.snow.json.Jsonable;
import com.zoe.snow.model.support.BaseModelSupport;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * School
 *
 * @author Dai Wenqing
 * @date 2016/7/8
 */
@Entity
@Table(name = "crip_school")
@Component("crip.user.school")
public class School extends BaseModelSupport {

    private String schoolName;

    @Column(name = "school_name")
    @Jsonable
    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
