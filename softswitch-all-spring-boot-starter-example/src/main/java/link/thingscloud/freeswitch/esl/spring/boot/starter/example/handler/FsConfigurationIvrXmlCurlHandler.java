package link.thingscloud.freeswitch.esl.spring.boot.starter.example.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import link.thingscloud.freeswitch.esl.spring.boot.starter.propeties.OutboundClientProperties;
import link.thingscloud.freeswitch.xml.annotation.XmlCurlSectionName;
import link.thingscloud.freeswitch.xml.constant.SectionNames;
import link.thingscloud.freeswitch.xml.domain.XmlCurl;
import link.thingscloud.freeswitch.xml.domain.configuration.Configuration;
import link.thingscloud.freeswitch.xml.domain.configuration.ivr.Entry;
import link.thingscloud.freeswitch.xml.domain.configuration.ivr.Menu;
import link.thingscloud.freeswitch.xml.domain.configuration.ivr.Menus;
import link.thingscloud.freeswitch.xml.domain.dialplan.Action;
import link.thingscloud.freeswitch.xml.domain.dialplan.AppEnum;
import link.thingscloud.freeswitch.xml.handler.XmlCurlHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author th158
 */
@Slf4j
@Service
@XmlCurlSectionName(value = SectionNames.CONFIGURATION, key = "ivr.conf")
public class FsConfigurationIvrXmlCurlHandler implements XmlCurlHandler {
    @NacosInjected
    private NamingService namingService;

    @Autowired
    private OutboundClientProperties outboundClientProperties;

    @Override
    public String handleXmlCurl(XmlCurl xmlCurl) {
        log.info("IVR exampel handle xml curl : [{}]", JSON.toJSONString(xmlCurl, true));
        try {
            String xml = getConfiguration();
            log.info(xml);
            return xml;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    private String getConfiguration() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        Configuration<Menus> configuration = new Configuration<>();

        // todo
        configuration.setName("ivr.conf");
        configuration.setDescription("ivr menus");
        configuration.setList(getMenus());
        // todo
        return xmlMapper.writeValueAsString(configuration).replaceAll("list","menus");
    }

    private List<Menus> getMenus() {
        List<Menus> list = new ArrayList<>();
        list.add(new Menus(getMenu()));
        return list;
    }

    private List<Menu> getMenu(){
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
            Instance instance = namingService.selectOneHealthyInstance("softswitch-gateway");
            // 组装
            String arg = "socket " + instance.getIp() + ":" + outboundClientProperties.getServer().getPort() + " async full";
            list.add(new Entry("menu-exec-app","1", arg));
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return list;
    }
}

