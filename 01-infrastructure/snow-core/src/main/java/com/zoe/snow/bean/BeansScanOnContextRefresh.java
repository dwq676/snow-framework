package com.zoe.snow.bean;

import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * BeansOnContextRefreshScan
 *
 * @author Dai Wenqing
 * @date 2016/6/16
 */
@Component("snow.bean.scan")
public class BeansScanOnContextRefresh implements ContextRefreshedListener {
    @Autowired(required = false)
    private Set<BeansScanRegister> beansScanRegisterSet;

    @Override
    public int getContextRefreshedSort() {
        return 1;
    }

    @Override
    public void onContextRefreshed() {
        //循环bean之前做的操作
        if (!Validator.isEmpty(beansScanRegisterSet)) {
            beansScanRegisterSet.forEach(c -> {
                c.before();
            });
        }
        //循环中的操作
        for (String beanName : BeanFactory.getBeanNames()) {
            if (!Validator.isEmpty(beansScanRegisterSet)) {
                beansScanRegisterSet.forEach(c -> {
                    c.run(beanName);
                });
            }
        }
        //循环后的操作
        if (!Validator.isEmpty(beansScanRegisterSet)) {
            beansScanRegisterSet.forEach(c -> {
                c.after();
            });
        }
    }
}
