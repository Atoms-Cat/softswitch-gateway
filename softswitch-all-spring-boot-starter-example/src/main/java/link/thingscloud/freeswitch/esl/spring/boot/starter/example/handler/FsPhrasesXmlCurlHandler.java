package link.thingscloud.freeswitch.esl.spring.boot.starter.example.handler;

import com.alibaba.fastjson.JSON;
import link.thingscloud.freeswitch.xml.annotation.XmlCurlSectionName;
import link.thingscloud.freeswitch.xml.constant.SectionNames;
import link.thingscloud.freeswitch.xml.domain.XmlCurl;
import link.thingscloud.freeswitch.xml.handler.XmlCurlHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author th158
 */
@Slf4j
@Service
@XmlCurlSectionName(SectionNames.PHRASES)
public class FsPhrasesXmlCurlHandler implements XmlCurlHandler {
    @Override
    public String handleXmlCurl(XmlCurl xmlCurl) {
        log.info("PHRASES exampel handle xml curl : [{}]", JSON.toJSONString(xmlCurl, true));
        return null;
    }
}
