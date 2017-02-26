package com.zoe.snow.test.support;

import com.zoe.snow.model.support.user.BaseModelHelperImpl;
import com.zoe.snow.test.School;
import com.zoe.snow.test.Student;
import com.zoe.snow.test.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * BaseModelHelperImplTest
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/2/26
 */
@Service
@ContextConfiguration(locations = {"/configuration.spring.xml"})
public class BaseModelHelperImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private BaseModelHelperImpl baseModelHelper;

    @Test
    public void testInitModel() {
        UserModel userModel = new UserModel();
        Student student = new Student();
        userModel.setStudent(student);
        School school = new School();
        student.setSchool(school);
        baseModelHelper.initBaseModel(userModel);
    }

}
