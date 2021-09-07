package com.atomscat.freeswitch.esl.spring.boot.starter.example.handler;

import com.alibaba.fastjson.JSON;
import com.atomscat.freeswitch.xml.annotation.XmlCurlSectionName;
import com.atomscat.freeswitch.xml.constant.SectionNames;
import com.atomscat.freeswitch.xml.domain.XmlCurl;
import com.atomscat.freeswitch.xml.handler.XmlCurlHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * https://freeswitch.org/confluence/display/FREESWITCH/Speech+Phrase+Management
 * https://freeswitch.org/confluence/display/FREESWITCH/mod_dptools%3A+phrase
 * https://freeswitch.org/confluence/display/FREESWITCH/Configuration
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
