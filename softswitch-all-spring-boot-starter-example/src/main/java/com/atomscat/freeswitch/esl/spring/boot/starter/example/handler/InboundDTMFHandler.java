package com.atomscat.freeswitch.esl.spring.boot.starter.example.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.atomscat.freeswitch.esl.InboundClient;
import com.atomscat.freeswitch.esl.constant.EventNames;
import com.atomscat.freeswitch.esl.spring.boot.starter.annotation.EslEventName;
import com.atomscat.freeswitch.esl.spring.boot.starter.handler.EslEventHandler;
import com.atomscat.freeswitch.esl.spring.boot.starter.propeties.OutboundClientProperties;
import com.atomscat.freeswitch.esl.transport.SendMsg;
import com.atomscat.freeswitch.esl.transport.event.EslEvent;
import com.atomscat.freeswitch.esl.util.EslEventUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Slf4j
@EslEventName(EventNames.DTMF)
@Component
public class InboundDTMFHandler implements EslEventHandler {

    @Autowired
    private InboundClient inboundClient;

    @NacosInjected
    private NamingService namingService;

    @Autowired
    private OutboundClientProperties outboundClientProperties;

    @Override
    public void handle(String address, EslEvent event, String coreUUID) {
        SendMsg sendMsg = new SendMsg(EslEventUtil.getCallerUniqueId(event));

        try {
            log.info("Inbound DTMF:[{}] [{}]  [{}]", address, coreUUID, JSON.toJSONString(event));

            // 根据服务名从注册中心获取一个健康的服务实例
            Instance instance = namingService.selectOneHealthyInstance("softswitch-gateway");
            // 向fs 发送 socket 信息
            sendMsg.addCallCommand("execute");
            sendMsg.addExecuteAppName("socket");
            // 组装  <action application="socket" data=" IP : yaml配置的端口 async full" />
            String arg = instance.getIp() + ":" + outboundClientProperties.getServer().getPort() + " async full";

            log.info("instance socket: ip [{}] port [{}], arg: [{}]", instance.getIp(), outboundClientProperties.getServer().getPort(), arg);
            sendMsg.addExecuteAppArg(arg);
            inboundClient.sendMessage(address, sendMsg);

            //
//            sendMsg.addCallCommand("execute");
//            sendMsg.addExecuteAppName("bridge");
            // todo ACD(Automatic Call Distributor) 自动呼叫分配
            // todo get opensips sip adder
            //sendMsg.addExecuteAppArg("sofia/external/1024@192.168.10.109");
            // todo keepalived sip adder
//            sendMsg.addExecuteAppArg("sofia/external/1004@192.168.10.140");
//            inboundClient.sendMessage(address, sendMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
