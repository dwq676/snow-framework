package com.zoe.snow.model.support.user;

import java.sql.Timestamp;

import com.zoe.snow.model.support.BaseModel;
import com.zoe.snow.model.support.Sort;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dwq676 on 2015/8/25.
 */
@Component("snow.model.support.user.helper")
public class BaseModelHelperImpl implements BaseModelHelper {

    @Autowired(required = false)
    private UserHelper userHelper;

    @Override
    public void initBaseModel(BaseModel baseModel) {
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
                if (!Validator.isEmpty(sort))
                    sort.setSort(0);
            }
        }
    }

}
