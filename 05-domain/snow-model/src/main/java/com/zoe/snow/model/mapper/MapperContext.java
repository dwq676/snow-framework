package com.zoe.snow.model.mapper;

import com.zoe.snow.model.Model;
import com.zoe.snow.model.annotation.Rule;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/10/8
 */
@Component("snow.model.mapper.context")
public class MapperContext implements  ValidatorMapper {
    @Override
    public Map<String, List<Rule>> getRule(Class<? extends Model> classZ) {
        return null;
    }
}
