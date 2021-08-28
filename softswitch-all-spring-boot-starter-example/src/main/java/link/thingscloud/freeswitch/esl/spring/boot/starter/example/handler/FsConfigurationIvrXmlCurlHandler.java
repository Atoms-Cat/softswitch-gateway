package link.thingscloud.freeswitch.esl.spring.boot.starter.example.handler;

import link.thingscloud.freeswitch.xml.annotation.XmlCurlSectionName;
import link.thingscloud.freeswitch.xml.constant.SectionNames;
import link.thingscloud.freeswitch.xml.domain.XmlCurl;
import link.thingscloud.freeswitch.xml.handler.XmlCurlHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@XmlCurlSectionName(SectionNames.CONFIGURATION)
public class FsConfigurationIvrXmlCurlHandler implements XmlCurlHandler {
    @Override
    public String handleXmlCurl(XmlCurl xmlCurl) {
        return null;
    }
}
