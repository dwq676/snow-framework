package com.zoe.snow.conf.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * ConfigurationImpl
 *
 * @author Dai Wenqing
 * @date 2016/1/6
 */
@Component("snow.property.conf")
public class ConfigurationImpl implements Configuration {
    @Autowired(required = false)
    private Set<Configurable> configurableSet;

    @Override
    public Map<String, Element> getConfigureElements() {
        return null;
    }

    @Override
    public Object setConfigure(Map<String, Element> configMap) {
        return null;
    }
}
