package com.zoe.snow.model.support.user;

import java.lang.reflect.Method;
import java.sql.Timestamp;

import com.zoe.snow.log.Logger;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.support.*;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 实体的初始化，与用户相关
 * Created by dwq676 on 2015/8/25.
 */
@Component("snow.model.support.user.helper")
public class ModelHelperImpl implements ModelHelper {

    @Autowired(required = false)
    private UserHelper userHelper;

    @Override
    public void initModel(Model model) {
        if (model == null)
            return;
        //初始化BaseModel中字段
        //initBaseModelField(model);
        if (model instanceof Domain)
            initDomain((Domain) model);
        if (model instanceof UserAtBy)
            initUserAtBy(model, (UserAtBy) model);
        if (model instanceof ValidFlag)
            initValid(model, (ValidFlag) model);
        //遍历递归实体是否嵌套有类型为BaseModel的字段
        initModelOfBaseModelField(model);
    }

    private void initDomain(Domain domain) {
        if (userHelper == null)
            return;
        if (domain.getDomain() == null) {
            domain.setDomain(userHelper.getDomain());
        }
    }

    private void initValid(Model model, ValidFlag validFlag) {
        //初始化
        if (Validator.isEmpty(model.getId()))
            validFlag.setValidFlag(1);
    }

    private void initUserAtBy(Model model, UserAtBy userAtBy) {
        if (userHelper == null)
            return;
        if (userAtBy.getCreatedAt() == null) {
            userAtBy.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        }

        if (userAtBy.getUpdatedAt() == null) {
            userAtBy.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        } else {
            if (!Validator.isEmpty(model.getId()))
                userAtBy.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        }

        if (userAtBy.getUpdatedBy() == null) {
            userAtBy.setUpdatedBy(userHelper.getUsername());
        } else {
            if (!Validator.isEmpty(model.getId()))
                userAtBy.setUpdatedBy(userHelper.getUsername());
        }
        if (userAtBy.getCreatedBy() == null) {
            userAtBy.setCreatedBy(userHelper.getUsername());
        }
    }


    //递归初始化
    private void initModelOfBaseModelField(Model model) {
        for (Method method : model.getClass().getMethods()) {
            if (method.getName().startsWith("get")) {
                try {
                    Object o = method.invoke(model);
                    if (o instanceof Model) {
                        try {
                            initModel((Model) o);
                        } catch (Exception e) {
                            Logger.warn(e, "嵌套实例化对象发现了异常，父对象类型为[{}]，当前对象类型为[{}]"
                                    , model.getClass(), method.getReturnType());
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    private void initBaseModelField(Model baseModel) {
                /*if (Validator.isEmpty(baseModel.getId())) {
            if (baseModel instanceof Sort) {
                Sort sort = Sort.class.cast(baseModel);
                if (!Validator.isEmpty(sort)) {
                    if (sort.getSort() == Integer.MIN_VALUE)
                        sort.setSort(0);
                }
            }
        }*/
    }

}
