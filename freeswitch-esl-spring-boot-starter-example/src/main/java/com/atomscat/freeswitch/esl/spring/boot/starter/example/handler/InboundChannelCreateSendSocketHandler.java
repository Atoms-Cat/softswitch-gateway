package com.atomscat.freeswitch.esl.spring.boot.starter.example.handler;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.atomscat.freeswitch.esl.InboundClient;
import com.atomscat.freeswitch.esl.constant.EventNames;
import com.atomscat.freeswitch.esl.helper.EslHelper;
import com.atomscat.freeswitch.esl.spring.boot.starter.annotation.EslEventName;
import com.atomscat.freeswitch.esl.spring.boot.starter.handler.InboundEventHandler;
import com.atomscat.freeswitch.esl.spring.boot.starter.propeties.OutboundServerProperties;
import com.atomscat.freeswitch.esl.transport.SendMsg;
import com.atomscat.freeswitch.esl.transport.event.EslEvent;
import com.atomscat.freeswitch.esl.util.EslEventUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//* todo FS --> [Inbound] --> app --> [sendMsg] --> socket address
//* todo FS <--> [Outbound] <--> app
//* todo 分布式锁

/**
 * @author th158
 */
@Slf4j
@EslEventName(EventNames.CHANNEL_CREATE)
@Component
public class InboundChannelCreateSendSocketHandler implements InboundEventHandler {

    @NacosInjected
    private NamingService namingService;

    @Autowired
    private InboundClient inboundClient;

    @Autowired
    private OutboundServerProperties outboundServerProperties;

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(String address, EslEvent event, String coreUUID) {
        SendMsg sendMsg = new SendMsg(EslEventUtil.getCallerUniqueId(event));

        try {
            // 判断 是否 是 inbound 处理
            if ("inbound".equals(EslEventUtil.getCallerDirection(event))) {
                // 根据服务名从注册中心获取一个健康的服务实例
                Instance instance = namingService.selectOneHealthyInstance("fs-esl");
                // 向fs 发送 socket 信息
                sendMsg.addCallCommand("execute");
                sendMsg.addExecuteAppName("socket");
                // 组装  <action application="socket" data=" IP : yaml配置的端口 async full" />
                String arg = instance.getIp() + ":" + outboundServerProperties.getServer().getPort() + " async full";

                log.info("instance socket: ip [{}] port [{}], arg: [{}]", instance.getIp(), outboundServerProperties.getServer().getPort(), arg);
                sendMsg.addExecuteAppArg(arg);

                inboundClient.sendMessage(address, sendMsg);
            }

        } catch (NacosException e) {
            e.printStackTrace();
        }
        log.debug("address[{}] EslEvent[{}]", address, EslHelper.formatEslEvent(event));
    }


}
