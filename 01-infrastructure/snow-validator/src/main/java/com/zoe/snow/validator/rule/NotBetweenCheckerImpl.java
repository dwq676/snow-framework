package com.zoe.snow.validator.rule;

import com.zoe.snow.validator.Checker;
import com.zoe.snow.util.Converter;
import org.springframework.stereotype.Component;

/**
 * NotBetweenCheckerImpl
 *
 * @author Dai Wenqing
 * @date 2015/12/7
 */
@Component("error.parameter.not-between")
public class NotBetweenCheckerImpl implements Checker {
    @Override
    public boolean validate(Object value, Object... parameters) {
        if (value == null)
            return false;
        if (parameters.length < 2)
            return false;
        double n = Converter.toDouble(value);
        return n >= Converter.toDouble(parameters[0]) && n <= Converter.toDouble(parameters[1]);
    }

    @Override
    public String getName() {
        return "not-between";
    }
}
