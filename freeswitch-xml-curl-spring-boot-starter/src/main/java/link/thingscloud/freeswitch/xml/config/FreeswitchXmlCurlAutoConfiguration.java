package link.thingscloud.freeswitch.xml.config;


import link.thingscloud.freeswitch.xml.handler.SimpleXmlCurlHandler;
import link.thingscloud.freeswitch.xml.handler.XmlCurlHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>FreeswitchCdrAutoConfiguration class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Configuration
@ComponentScan({"link.thingscloud.freeswitch.xml"})
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
