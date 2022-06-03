package com.atomscat.freeswitch.cdr.spring.boot.starter.config;

import com.atomscat.freeswitch.cdr.handler.CdrHandler;
import com.atomscat.freeswitch.cdr.spring.boot.starter.handler.SimpleCdrHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>FreeswitchCdrAutoConfiguration class.</p>
 *
 * @author : <a href="everyone@aliyun.com">everyone</a>
 * @version $Id: $Id
 */
@Configuration
@ComponentScan({"com.atomscat.freeswitch.cdr"})
public class FreeswitchCdrAutoConfiguration {


    /**
     * <p>cdrHandler.</p>
     *
     * @return a {@link CdrHandler} object.
     */
    @Bean
    @ConditionalOnMissingBean(CdrHandler.class)
    public CdrHandler cdrHandler() {
        return new SimpleCdrHandler();
    }

}
