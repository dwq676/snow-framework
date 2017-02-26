package com.zoe.snow.model.support.user;

import java.lang.reflect.Method;
import java.sql.Timestamp;

import com.zoe.snow.log.Logger;
import com.zoe.snow.model.support.BaseModel;
import com.zoe.snow.model.support.Sort;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 实体的初始化，与用户相关
 * Created by dwq676 on 2015/8/25.
 */
@Component("snow.model.support.user.helper")
public class BaseModelHelperImpl implements BaseModelHelper {

    @Autowired(required = false)
    private UserHelper userHelper;

    @Override
    public void initBaseModel(BaseModel baseModel) {
        //初始化BaseModel中字段
        initBaseModelField(baseModel);
        //遍历递归实体是否嵌套有类型为BaseModel的字段
        initModelOfBaseModelField(baseModel);
    }

    //递归初始化
    private void initModelOfBaseModelField(BaseModel baseModel) {
        if (Validator.isEmpty(baseModel)) return;
        for (Method method : baseModel.getClass().getMethods()) {
            if (method.getName().startsWith("get")) {
                try {
                    Object o = method.invoke(baseModel);
                    if (o instanceof BaseModel) {
                        try {
                            initBaseModel((BaseModel) o);
                        } catch (Exception e) {
                            Logger.warn(e, "嵌套实例化对象发现了异常，父对象类型为[{}]，当前对象类型为[{}]"
                                    , baseModel.getClass(), method.getReturnType());
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    private void initBaseModelField(BaseModel baseModel) {
        if (Validator.isEmpty(baseModel))
            return;
        if (baseModel.getCreateTime() == null) {
            baseModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
        }
        // if (baseModel.getValidFlag() == -1) {
        if (Validator.isEmpty(baseModel.getId()))
            baseModel.setValidFlag(1);
        // }
        if (baseModel.getModifyTime() == null) {
            baseModel.setModifyTime(new Timestamp(System.currentTimeMillis()));
        } else {
            if (!Validator.isEmpty(baseModel.getId()))
                baseModel.setModifyTime(new Timestamp(System.currentTimeMillis()));
        }
        if (baseModel.getRemark() == null) {
            baseModel.setRemark("");
        }
        if (userHelper == null)
            return;
        if (baseModel.getDomain() == null) {
            baseModel.setDomain(userHelper.getDomain());
        }
        if (baseModel.getModifyUser() == null) {
            baseModel.setModifyUser(userHelper.getUsername());
        } else {
            if (!Validator.isEmpty(baseModel.getId()))
                baseModel.setModifyUser(userHelper.getUsername());
        }
        if (baseModel.getCreateUser() == null) {
            baseModel.setCreateUser(userHelper.getUsername());
        }
        if (Validator.isEmpty(baseModel.getId())) {
            if (baseModel instanceof Sort) {
                Sort sort = Sort.class.cast(baseModel);
                if (!Validator.isEmpty(sort)) {
                    if (sort.getSort() == Integer.MIN_VALUE)
                        sort.setSort(0);
                }
            }
        }
    }

}
