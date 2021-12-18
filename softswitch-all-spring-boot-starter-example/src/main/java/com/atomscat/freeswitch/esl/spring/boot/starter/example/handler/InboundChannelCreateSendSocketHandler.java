package com.atomscat.freeswitch.esl.spring.boot.starter.example.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.atomscat.freeswitch.esl.InboundClient;
import com.atomscat.freeswitch.esl.constant.EventNames;
import com.atomscat.freeswitch.esl.helper.EslHelper;
import com.atomscat.freeswitch.esl.spring.boot.starter.annotation.EslEventName;
import com.atomscat.freeswitch.esl.spring.boot.starter.handler.EslEventHandler;
import com.atomscat.freeswitch.esl.spring.boot.starter.propeties.OutboundClientProperties;
import com.atomscat.freeswitch.esl.transport.SendMsg;
import com.atomscat.freeswitch.esl.transport.event.EslEvent;
import com.atomscat.freeswitch.esl.transport.message.EslMessage;
import com.atomscat.freeswitch.esl.util.EslEventUtil;
import com.atomscat.spring.boot.common.aop.annotation.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
// * do: FS --> [Inbound] --> app --> [sendMsg] --> socket address
// * do: FS <--> [Outbound] <--> app

/**
 * @author th158
 */
@Slf4j
@EslEventName(EventNames.CHANNEL_CREATE)
@Component
public class InboundChannelCreateSendSocketHandler implements EslEventHandler {

    @NacosInjected
    private NamingService namingService;

    @Autowired
    private InboundClient inboundClient;

    @Autowired
    private OutboundClientProperties outboundClientProperties;

    /**
     * {@inheritDoc}
     */
    @Override
    @RedisLock(lockName = EventNames.CHANNEL_CREATE, key = "coreUUID")
    public void handle(String address, EslEvent event, String coreUUID) {
        SendMsg sendMsg = new SendMsg(EslEventUtil.getCallerUniqueId(event));

        log.info("Inbound CHANNEL_CREATE:[{}] [{}]  [{}]", address, coreUUID, JSON.toJSONString(event));

        try {
            // 判断 是否 是 inbound 处理
            if ("inbound".equals(EslEventUtil.getCallerDirection(event))) {
                // 根据服务名从注册中心获取一个健康的服务实例
                Instance instance = namingService.selectOneHealthyInstance("softswitch-gateway");
                // 向fs 发送 socket 信息
                sendMsg.addCallCommand("execute");
                sendMsg.addExecuteAppName("socket");
                // 组装  <action application="socket" data=" IP : yaml配置的端口 async full" />
                String arg = instance.getIp() + ":" + outboundClientProperties.getServer().getPort() + " async full";

                log.info("instance socket: ip [{}] port [{}], arg: [{}]", instance.getIp(), outboundClientProperties.getServer().getPort(), arg);
                sendMsg.addExecuteAppArg(arg);
                //inboundClient.sendMessage(address, sendMsg);


//                String callerUUID = EslEventUtil.getCallChannelUuid(event);
//
//                EslMessage phoneHold = inboundClient.sendSyncApiCommand(address, "uuid_phone_event", callerUUID + " hold");
//                log.info("phoneHold: {}", JSON.toJSONString(phoneHold));
//
//                EslMessage eslMessageUUID = inboundClient.sendSyncApiCommand(address, "create_uuid","");
//                String calleeUUID =  eslMessageUUID.getBodyLines().get(0);
//                log.info("create_uuid:  {}", calleeUUID);
//
//                EslMessage originate =  inboundClient.sendSyncApiCommand(address, "originate", "{origination_uuid="+ calleeUUID +"}sofia/external/" + EslEventUtil.getSipToUri(event) + " &park");
//                log.info("originate: {}", JSON.toJSONString(originate));
//
//                EslMessage uuidBridge =  inboundClient.sendSyncApiCommand(address, "uuid_bridge", calleeUUID + " " + callerUUID);
//                log.info("uuidBridge: {}", JSON.toJSONString(uuidBridge));
//
//                EslMessage phoneTalk = inboundClient.sendSyncApiCommand(address, "uuid_phone_event", callerUUID + " talk");
//                log.info("phoneTalk: {}", JSON.toJSONString(phoneTalk));
//
//                // uuid_bridge

                // todo 应用选举 opensips 功能 --- ws tcp 长连接
                // {media_webrtc=true,sip_invite_req_uri=sip:$1@atomscat.com,sip_route_uri=sip:$1@192.168.10.114:5060}sofia/external/$1@192.168.10.116:5060
                // {media_webrtc=true,sip_invite_req_uri=sip:$1@[域],sip_route_uri=sip:$1@[opensips地址]}sofia/external/$1@[opensips地址]
            }

            EslMessage eslMessage = inboundClient.sendSyncApiCommand(address, "show", "channels as json");
            log.info("show channels as json {}", JSON.toJSONString(eslMessage));

        } catch (NacosException e) {
            e.printStackTrace();
        }
        log.debug("address[{}] EslEvent[{}]", address, EslHelper.formatEslEvent(event));
    }


}
