package link.thingscloud.freeswitch.esl.spring.boot.starter.example.handler;

import com.alibaba.fastjson.JSON;
import link.thingscloud.freeswitch.cdr.domain.Cdr;
import link.thingscloud.freeswitch.cdr.handler.CdrHandler;
import link.thingscloud.freeswitch.xml.domain.XmlCurl;
import link.thingscloud.freeswitch.xml.handler.XmlCurlHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>ExampleCdrHandler class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Slf4j
@Service
public class FsXmlCurlHandler implements XmlCurlHandler {
    /**
     * {@inheritDoc}
     */
    @Override
    public String handleXmlCurl(XmlCurl cdr) {
        log.info("exampel handle cdr : [{}]", JSON.toJSONString(cdr, true));
        return "";
    }

}
