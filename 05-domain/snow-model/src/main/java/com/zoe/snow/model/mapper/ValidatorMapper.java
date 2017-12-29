package com.zoe.snow.model.mapper;

import com.zoe.snow.model.Model;
import com.zoe.snow.model.annotation.Rule;

import java.util.List;
import java.util.Map;

/**
 * ValidatorMapper
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/10/8
 */
public interface ValidatorMapper {
    /**
     * 获取字段规则列表
     *
     * @param classZ
     * @return 以field name为key的规则列表
     */
    Map<String, List<Rule>> getRule(Class<? extends Model> classZ);


}
