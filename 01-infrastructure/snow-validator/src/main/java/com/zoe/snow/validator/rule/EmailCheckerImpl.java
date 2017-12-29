package com.zoe.snow.validator.rule;

import com.zoe.snow.validator.Checker;
import org.springframework.stereotype.Component;

/**
 * EmailCheckerImpl
 *
 * @author Dai Wenqing
 * @date 2015/12/7
 */
@Component("error.parameter.illegal-email")
public class EmailCheckerImpl extends DefaultCheckerImpl implements Checker {

    @Override
    public boolean validate(Object value, Object... parameters) {
        this.regEx = "^[\\.a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        return super.validate(value, parameters);
    }

    @Override
    public String getName() {
        return "illegal-email";
    }
}
