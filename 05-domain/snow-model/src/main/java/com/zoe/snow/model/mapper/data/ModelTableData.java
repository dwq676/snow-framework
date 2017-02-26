package com.zoe.snow.model.mapper.data;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ModelTableData
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/2/20
 */
@Repository("snow.model.mapper.data.table")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ModelTableData {
    private Map<Class<CustomAnnotationData>, CustomAnnotationData> annotationDataMap = new ConcurrentHashMap<>();
}
