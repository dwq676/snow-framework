package com.zoe.snow.model;

import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.util.Generator;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * IdGenerator
 *
 * @author Dai Wenqing
 * @date 2016/10/26
 */
@Repository("snow.model.generator")
public class IdGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
        CustomGenerator customGenerator = BeanFactory.getBean(CustomGenerator.class);
        if (customGenerator == null)
            return Generator.uuid();
        return customGenerator.generate();
    }

   /* @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {

    }*/
}
