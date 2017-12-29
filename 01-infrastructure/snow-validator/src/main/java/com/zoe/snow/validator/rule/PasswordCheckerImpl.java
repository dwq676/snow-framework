package com.zoe.snow.validator.rule;

import com.zoe.snow.util.Converter;
import com.zoe.snow.util.Validator;
import com.zoe.snow.validator.Checker;
import org.springframework.stereotype.Repository;

/**
 * PasswordCheckerImpl
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/9/22
 */
@Repository("error.parameter.illegal-password")
public class PasswordCheckerImpl implements Checker {
    @Override
    public boolean validate(Object value, Object... parameters) {
        if (Validator.isEmpty(value))
            return false;
        if (parameters == null)
            return false;
        if (parameters.length < 1)
            return false;
        if (String.valueOf(value).length() < Converter.toInt(parameters[0]))
            return false;
        return true;
    }

    @Override
    public String getName() {
        return "illegal-password";
    }
}
