package com.zoe.snow.test.support;

import com.zoe.snow.crud.CrudService;
import com.zoe.snow.test.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * QueryTest
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/2/27
 */
@Service
@ContextConfiguration(locations = {"/configuration.spring.xml"})
public class QueryTest extends AbstractTestNGSpringContextTests {

    @Test
    public void predicateTest() {
        //crudService.query().where()
        UserModel userModel = new UserModel();

        //crudService.query().where(()->"(id like ? and userName like ?)");
    }
}
