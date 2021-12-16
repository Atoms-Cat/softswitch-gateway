package com.atomscat.freeswitch.esl.spring.boot.starter.example.handler;

import com.alibaba.fastjson.JSON;
import com.atomscat.freeswitch.xml.annotation.XmlCurlSectionName;
import com.atomscat.freeswitch.xml.constant.SectionNames;
import com.atomscat.freeswitch.xml.domain.XmlCurl;
import com.atomscat.freeswitch.xml.handler.XmlCurlHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * fs 執行：reload mod_sofia 重新加載sofia.conf配置
 * 动态配置 external 的 gateway配置
 * freeswitch 启动或者 执行 reload mod_sofia 指令才会获取
 */
@Slf4j
@Service
@XmlCurlSectionName(value = SectionNames.CONFIGURATION, key = SectionNames.Configuration.SOFIA)
public class FsSofiaXmlCurlHandler implements XmlCurlHandler {
    @Override
    public String handleXmlCurl(XmlCurl xmlCurl) {
        log.info("FsSofiaXmlCurlHandler  : [{}] [{}]", JSON.toJSONString(xmlCurl, true), xmlCurl);


        return "<configuration name=\"sofia.conf\" description=\"sofia endpoint\">\n" +
                "  <profiles>\n" +
                "    <profile name=\"example\">\n" +
                "      <gateways>\n" +
                "         <gateway name=\"asterisk_1\">\n" +
                "               <param name=\"realm\" value=\"1.2.3.4:5060\"/>\n" +
                "               <param name=\"username\" value=\"foo\"/>\n" +
                "               <param name=\"password\" value=\"1234\"/>\n" +
                "               <param name=\"register\" value=\"false\"/>\n" +
                "               <param name=\"retry-seconds\" value=\"30\"/>\n" +
                "           </gateway>\n" +
                "      </gateways>\n" +
                "      <settings> \n" +
                "      </settings>\n" +
                "    </profile>\n" +
                "  </profiles>\n" +
                "</configuration>";
    }
}
