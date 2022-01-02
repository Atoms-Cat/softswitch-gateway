package com.atomscat.freeswitch.esl.spring.boot.starter.example.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.naming.NamingService;
import com.atomscat.freeswitch.esl.spring.boot.starter.propeties.OutboundClientProperties;
import com.atomscat.freeswitch.xml.annotation.XmlCurlSectionName;
import com.atomscat.freeswitch.xml.constant.SectionNames;
import com.atomscat.freeswitch.xml.domain.XmlCurl;
import com.atomscat.freeswitch.xml.domain.configuration.ivr.Entry;
import com.atomscat.freeswitch.xml.domain.configuration.ivr.Ivr;
import com.atomscat.freeswitch.xml.domain.configuration.ivr.Menu;
import com.atomscat.freeswitch.xml.domain.configuration.ivr.Menus;
import com.atomscat.freeswitch.xml.handler.XmlCurlHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * https://freeswitch.org/confluence/display/FREESWITCH/Configuration
 *
 * @author th158
 */
@Slf4j
@Service
@XmlCurlSectionName(value = SectionNames.CONFIGURATION, key = SectionNames.Configuration.IVR)
public class FsConfigurationIvrXmlCurlHandler implements XmlCurlHandler {
    @NacosInjected
    private NamingService namingService;

    @Autowired
    private OutboundClientProperties outboundClientProperties;

    @Override
    public String handleXmlCurl(XmlCurl xmlCurl) {
        try {
            String xml = getConfiguration();
            log.info("IVR exampel handle xml curl : [{}] [{}]", JSON.toJSONString(xmlCurl, true), xml);
            return xml;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getConfiguration() throws JsonProcessingException {
        Ivr configuration = new Ivr();
        configuration.setDescription("ivr menus");
        configuration.setMenus(getMenus());
        return configuration.toXmlString();
    }

    private Menus getMenus() {
        return new Menus(getMenu());
    }

    private List<Menu> getMenu() {
        List<Menu> list = new ArrayList<>();
        Menu menu = new Menu();
        menu.setName("test_ivr");
        menu.setGreetLong("phrase:demo_ivr_main_menu");
        menu.setTimeout("10000");
        menu.setInterDigitTimeout("2000");
        menu.setEntry(getEntry());
        list.add(menu);
        return list;
    }

    private List<Entry> getEntry() {
        List<Entry> list = new ArrayList<>();
        try {
            // 组装，触发数字按键 1，执行事件
            // Instance instance = namingService.selectOneHealthyInstance("softswitch-gateway");
            // String arg = "socket " + instance.getIp() + ":" + outboundClientProperties.getServer().getPort() + " async full";
            String arg = "sleep 10000";
            list.add(new Entry("menu-exec-app", "1", arg));
            // 组装，触发数字按键 2，执行事件
            // todo

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

