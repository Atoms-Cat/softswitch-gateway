package com.atomscat.freeswitch.esl.spring.boot.starter.example.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.atomscat.freeswitch.esl.spring.boot.starter.propeties.OutboundClientProperties;
import com.atomscat.freeswitch.xml.annotation.XmlCurlSectionName;
import com.atomscat.freeswitch.xml.constant.SectionNames;
import com.atomscat.freeswitch.xml.domain.XmlCurl;
import com.atomscat.freeswitch.xml.domain.dialplan.*;
import com.atomscat.freeswitch.xml.handler.XmlCurlHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>ExampleCdrHandler class.</p>
 *
 * @author : <a href="everyone@aliyun.com">everyone</a>
 * @version $Id: $Id
 */
@Slf4j
@Service
@XmlCurlSectionName(SectionNames.DIALPLAN)
public class FsDialplanXmlCurlHandler implements XmlCurlHandler {

    @NacosInjected
    private NamingService namingService;

    @Autowired
    private OutboundClientProperties outboundClientProperties;

    /**
     * {@inheritDoc}
     */
    @Override
    public String handleXmlCurl(XmlCurl cdr) {
        String xml = "";
        try {
            xml += getContext("public");
            xml += getContext("default");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.debug("exampel handle xml curl : [{}] [{}]", JSON.toJSONString(cdr, true), xml);
        return xml;
    }

    private String getContext(String name) throws JsonProcessingException {
        Context context = new Context();
        context.setName(name);
        // todo
        context.setExtension(getExtension());
        return context.toXmlString();
    }


    private List<Extension> getExtension() {
        List<Extension> extensionlist = new ArrayList<>();

        // 默认拨号计划
        Extension extension = new Extension();
        extension.setName("call");
        // todo
        extension.setCondition(getCondition());
        extensionlist.add(extension);

        // 语音网关 拨号计划 voice gateway
        extension = new Extension();
        extension.setName("voice_gateway");
        // todo
        extension.setCondition(getVoiceCondition());
        extensionlist.add(extension);
        return extensionlist;
    }

    private List<Condition> getCondition() {
        List<Condition> conditionList = new ArrayList<>();

        Condition condition = new Condition();
        condition.setField("destination_number");
        condition.setExpression("^[1-9]([0-9]\\d+)$");
        // todo
        condition.setAction(getAction());

        conditionList.add(condition);
        return conditionList;
    }

    private List<Action> getAction() {
        List<Action> actionList = new ArrayList<>();
        // todo
        actionList.add(new Action(AppEnum.ANSWER, null));
        actionList.add(new Action(AppEnum.SLEEP, "1000"));
        // <action application="ivr" data="demo_ivr"/>
        // actionList.add(new Action(AppEnum.ivr, "test_ivr"));
        // 根据服务名从注册中心获取一个健康的服务实例
        try {
            Instance instance = namingService.selectOneHealthyInstance("softswitch-gateway");
            // 组装  <action application="socket" data=" IP : yaml配置的端口 async full" />
            //String arg = instance.getIp() + ":" + outboundClientProperties.getServer().getPort() + " async full";
            //actionList.add(new Action(AppEnum.socket, arg));
            String arg = "sofia/external/${sip_to_uri}";
            actionList.add(new Action(AppEnum.BRIDGE, arg));
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return actionList;
    }

    /**
     * 走语音网关 拨号计划
     *
     * @return
     */
    private List<Condition> getVoiceCondition() {
        List<Condition> conditionList = new ArrayList<>();

        Condition condition = new Condition();
        condition.setField("destination_number");
        condition.setExpression("^0(.*)$");
        // todo
        condition.setAction(getVoiceAction());

        conditionList.add(condition);
        return conditionList;
    }

    private List<Action> getVoiceAction() {
        List<Action> actionList = new ArrayList<>();
        // 注意：X-Voice-Gateway 为自定义 fs-opensips 统一标识 外呼 走 语音网关
        actionList.add(new Action(AppEnum.EXPORT, "sip_h_X-Voice-Gateway=true"));
        // 自定义获取真实用户号码
        actionList.add(new Action(AppEnum.EXPORT, "sip_h_X-To-Real-User=$1"));
        actionList.add(new Action(AppEnum.ANSWER, null));
        actionList.add(new Action(AppEnum.SLEEP, "1000"));
        // 根据服务名从注册中心获取一个健康的服务实例
        try {
            Instance instance = namingService.selectOneHealthyInstance("softswitch-gateway");
            // 组装  <action application="socket" data=" IP : yaml配置的端口 async full" />
            String arg = instance.getIp() + ":" + outboundClientProperties.getServer().getPort() + " async full";
            actionList.add(new Action(AppEnum.SOCKET, arg));
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return actionList;
    }
}
