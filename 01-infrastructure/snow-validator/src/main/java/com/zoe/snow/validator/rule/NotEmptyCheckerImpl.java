package com.zoe.snow.validator.rule;

import com.zoe.snow.validator.Checker;
import com.zoe.snow.util.Validator;
import com.zoe.snow.validator.Dao;
import org.springframework.stereotype.Component;

/**
 * 不能为空验证器
 *
 * @author Dai Wenqing
 * @date 2015/12/7
 */
@Component("error.parameter.not.empty")
@Dao
public class NotEmptyCheckerImpl implements Checker {
    @Override
    public boolean validate(Object value, Object[] parameters) {
        if (value == null)
            return false;
        return value != null;
    }

    @Override
    public String getName() {
        return "not-empty";
    }
}
