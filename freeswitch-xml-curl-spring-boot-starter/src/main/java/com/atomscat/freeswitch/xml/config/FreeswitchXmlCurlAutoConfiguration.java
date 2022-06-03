package com.atomscat.freeswitch.xml.config;


import com.atomscat.freeswitch.xml.handler.SimpleXmlCurlHandler;
import com.atomscat.freeswitch.xml.handler.XmlCurlHandler;
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
@ComponentScan({"com.atomscat.freeswitch.xml"})
public class FreeswitchXmlCurlAutoConfiguration {


    /**
     * <p>cdrHandler.</p>
     *
     * @return a {@link XmlCurlHandler } object.
     */
    @Bean
    @ConditionalOnMissingBean(XmlCurlHandler.class)
    public XmlCurlHandler cdrHandler() {
        return new SimpleXmlCurlHandler();
    }

}
