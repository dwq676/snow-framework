package com.zoe.snow.validator.rule;

import com.zoe.snow.util.Validator;
import com.zoe.snow.validator.Checker;
import com.zoe.snow.validator.Dao;
import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * OverLengthException
 *
 * @author Dai Wenqing
 * @date 2016/10/19
 */
@Component("error.parameter.over.max.length")
@Dao
public class OverLengthCheckerImpl implements Checker {
    @Override
    public boolean validate(Object value, Object[] parameters) {
        //采取的策略是空值一定不超过长度
        if (Validator.isEmpty(value))
            return true;
        else if (value instanceof Date)
            return true;
            //无长度相关的参数，表明长度不受限制不需要验证
        else if (Validator.isEmpty(parameters))
            return true;
        else if (Validator.isNumeric(value))
            return true;
        else if (parameters.length < 1)
            return true;
        else if (value.toString().length() > Double.valueOf(parameters[0].toString()))
            return false;
        return true;
    }

    @Override
    public String getName() {
        return "over-length";
    }
}