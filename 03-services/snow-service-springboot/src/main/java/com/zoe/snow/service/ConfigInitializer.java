package com.zoe.snow.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 配置加载
 *
 * @author Dai Wenqing
 * @date 2016/11/21
 */
@Configuration
@ImportResource(locations={"classpath:application-bean.xml"})
public class ConfigInitializer {
}
