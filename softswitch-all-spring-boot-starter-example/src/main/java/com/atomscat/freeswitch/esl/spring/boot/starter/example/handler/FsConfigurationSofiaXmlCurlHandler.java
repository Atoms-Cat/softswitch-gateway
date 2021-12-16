package com.atomscat.freeswitch.esl.spring.boot.starter.example.handler;

import com.alibaba.fastjson.JSON;
import com.atomscat.freeswitch.xml.annotation.XmlCurlSectionName;
import com.atomscat.freeswitch.xml.constant.ConfName;
import com.atomscat.freeswitch.xml.constant.SectionNames;
import com.atomscat.freeswitch.xml.domain.XmlCurl;
import com.atomscat.freeswitch.xml.domain.configuration.Configuration;
import com.atomscat.freeswitch.xml.domain.configuration.ivr.Menus;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.Param;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.Sofia;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.gateway.Gateway;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.gateway.GatewayParamEnum;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.gateway.Gateways;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.profile.Profile;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.profile.Profiles;
import com.atomscat.freeswitch.xml.handler.XmlCurlHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * fs 執行：reload mod_sofia 重新加載sofia.conf配置
 * 动态配置 external 的 gateway配置
 * freeswitch 启动或者 执行 reload mod_sofia 指令才会获取
 */
@Slf4j
@Service
@XmlCurlSectionName(value = SectionNames.CONFIGURATION, key = SectionNames.Configuration.SOFIA)
public class FsConfigurationSofiaXmlCurlHandler implements XmlCurlHandler {
    @Override
    public String handleXmlCurl(XmlCurl xmlCurl) {
        log.info("FsConfigurationSofiaXmlCurlHandler  : [{}] [{}]", JSON.toJSONString(xmlCurl, true), xmlCurl);
        String xml = null;
        try {
            xml = getConfiguration();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return xml;
//
//        return "<configuration name=\"sofia.conf\" description=\"sofia endpoint\">\n" +
//                "  <profiles>\n" +
//                "    <profile name=\"example\">\n" +
//                "      <gateways>\n" +
//                "         <gateway name=\"asterisk_1\">\n" +
//                "               <param name=\"realm\" value=\"1.2.3.4:5060\"/>\n" +
//                "               <param name=\"username\" value=\"foo\"/>\n" +
//                "               <param name=\"password\" value=\"1234\"/>\n" +
//                "               <param name=\"register\" value=\"false\"/>\n" +
//                "               <param name=\"retry-seconds\" value=\"30\"/>\n" +
//                "           </gateway>\n" +
//                "      </gateways>\n" +
//                "      <settings> \n" +
//                "      </settings>\n" +
//                "    </profile>\n" +
//                "  </profiles>\n" +
//                "</configuration>";
    }

    private String getConfiguration() throws JsonProcessingException {
        Sofia configuration = new Sofia();
        configuration.setDescription("sofia endpoint");
        configuration.setProfiles(getProfiles());
        return configuration.toXmlString();
    }


    private Profiles getProfiles() {

        return new Profiles().setProfile(getProfileList());
    }

    private List<Profile> getProfileList() {
        List<Profile> profiles = new ArrayList<>();
        profiles.add(new Profile().setName("example").setGateways(new Gateways().setGateway(getGatewayList())));
        return profiles;
    }

    private List<Gateway> getGatewayList() {
        List<Gateway> gatewayList = new ArrayList<>();
        gatewayList.add(new Gateway().setName("asterisk").setParam(getParamList()));
        return gatewayList;
    }

    private List<Param> getParamList() {
        List<Param> paramList = new ArrayList<>();
        paramList.add(new Param(GatewayParamEnum.REALM.name,"1.2.3.4:5060"));
        paramList.add(new Param(GatewayParamEnum.USERNAME.name, "foo"));
        paramList.add(new Param(GatewayParamEnum.PASSWORD.name, "123456"));
        paramList.add(new Param(GatewayParamEnum.REGISTER.name, "false"));
        paramList.add(new Param(GatewayParamEnum.RETRY_SECONDS.name, "30"));
        return paramList;
    }

}
