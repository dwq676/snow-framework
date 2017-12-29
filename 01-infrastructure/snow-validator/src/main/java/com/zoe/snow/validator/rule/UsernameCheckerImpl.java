package com.zoe.snow.validator.rule;

import com.zoe.snow.util.Converter;
import com.zoe.snow.util.Validator;
import com.zoe.snow.validator.Checker;
import org.springframework.stereotype.Repository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PasswordCheckerImpl
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/9/22
 */
@Repository("error.parameter.illegal-username")
public class UsernameCheckerImpl extends DefaultCheckerImpl implements Checker {
    //private final String regEx = "^[a-zA-Z0-9_-]{4,16}$";

    @Override
    public boolean validate(Object value, Object... parameters) {
        this.regEx = "^[a-zA-Z0-9_-]{4,16}$";
        return super.validate(value, parameters);
    }

    @Override
    public String getName() {
        return "illegal-username";
    }
}
