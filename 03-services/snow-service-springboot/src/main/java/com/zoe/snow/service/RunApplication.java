package com.zoe.snow.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * RunApplication
 *
 * @author Dai Wenqing
 * @date 2016/11/20
 */
@Configuration
@ComponentScan(basePackages = {"com.zoe.snow"})
@EnableAutoConfiguration
public class RunApplication {
    public static void main(String[] args) {
        SpringApplication.run(RunApplication.class);
    }
}
