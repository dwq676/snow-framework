package com.zoe.snow.validator.rule;

import com.zoe.snow.util.Validator;
import com.zoe.snow.validator.Checker;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DefaultCheckerImpl
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/9/25
 */
@Component("checker.default")
public class DefaultCheckerImpl implements Checker{
    protected String regEx = "^[a-zA-Z0-9_-]{4,16}$";

    @Override
    public boolean validate(Object value, Object... parameters) {
        if (Validator.isEmpty(value))
            return false;
        /*if (parameters == null)
            return false;
        if (parameters.length < 1)
            return false;*/
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(value.toString());
        return matcher.find();
    }

    @Override
    public String getName() {
        return null;
    }
}
