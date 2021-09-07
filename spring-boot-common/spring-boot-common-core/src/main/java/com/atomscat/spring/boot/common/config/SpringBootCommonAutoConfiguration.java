package com.atomscat.spring.boot.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackages = {"com.atomscat.spring.boot.common"}
)
public class SpringBootCommonAutoConfiguration {
    public SpringBootCommonAutoConfiguration() {
    }
}
