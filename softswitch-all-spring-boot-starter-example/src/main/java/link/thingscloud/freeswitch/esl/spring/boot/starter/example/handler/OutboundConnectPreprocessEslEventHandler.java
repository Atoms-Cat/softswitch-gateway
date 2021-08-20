package link.thingscloud.freeswitch.esl.spring.boot.starter.example.handler;

import com.alibaba.fastjson.JSON;
import link.thingscloud.freeswitch.esl.outbound.handler.Context;
import link.thingscloud.freeswitch.esl.spring.boot.starter.handler.OutBoundConnectHandler;
import link.thingscloud.freeswitch.esl.spring.boot.starter.handler.OutBoundEventHandler;
import link.thingscloud.freeswitch.esl.transport.CommandResponse;
import link.thingscloud.freeswitch.esl.transport.SendMsg;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;
import link.thingscloud.freeswitch.esl.util.EslEventUtil;
import link.thingscloud.freeswitch.esl.util.RemotingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * fs-outbound 呼入
 */
@Slf4j
@Configuration
public class OutboundConnectPreprocessEslEventHandler implements OutBoundEventHandler, OutBoundConnectHandler {

    public final static String sofia = "sofia/external/";

    @Override
    public void onConnect(Context context, EslEvent eslEvent) {
        // 获取事件id

        String coreUUID = EslEventUtil.getCoreUuid(eslEvent);
        String remoteAddr = RemotingUtil.socketAddress2String(context.channel().remoteAddress());

        log.info("Outbound CHANNEL_CREATE:[{}] [{}]  [{}]", remoteAddr, coreUUID, JSON.toJSONString(eslEvent));

        List<SendMsg> bridgeMsgList = new ArrayList<>();
        SendMsg bridgeMsg = new SendMsg();
        bridgeMsg.addCallCommand("execute");
        bridgeMsg.addExecuteAppName("set");
        bridgeMsg.addExecuteAppArg("hangup_after_bridge=true");
        //context.sendMessage(bridgeMsg);
        bridgeMsgList.add(bridgeMsg);

        bridgeMsg = new SendMsg();
        bridgeMsg.addCallCommand("execute");
        bridgeMsg.addExecuteAppName("bridge");
        bridgeMsg.addExecuteAppArg(sofia + EslEventUtil.getSipToUri(eslEvent));
        bridgeMsg.addEventLock();
        bridgeMsgList.add(bridgeMsg);
        log.info("bridge to {}", EslEventUtil.getSipToUri(eslEvent));

        CommandResponse commandResponse = context.sendMessage(bridgeMsgList);
        log.info("response : {}", JSON.toJSONString(commandResponse));

        // 异步发送bridge命令接通
        //context.handler().sendAsyncMultiLineCommand(context.channel(), bridgeMsg.getMsgLines());

        List<SendMsg> sendMsgList = new ArrayList<>();

        SendMsg hangupMsg = new SendMsg();
        hangupMsg.addCallCommand("execute");
        hangupMsg.addExecuteAppName("answer");
        //context.handler().sendAsyncMultiLineCommand(context.channel(), hangupMsg.getMsgLines());
        //context.sendMessage(hangupMsg);
        sendMsgList.add(hangupMsg);

        hangupMsg = new SendMsg();
        hangupMsg.addCallCommand("execute");
        hangupMsg.addExecuteAppName("sleep");
        hangupMsg.addExecuteAppArg("1000");
        //context.handler().sendAsyncMultiLineCommand(context.channel(), hangupMsg.getMsgLines());
        sendMsgList.add(hangupMsg);

        // todo 评价或者 其他
        hangupMsg = new SendMsg();
        hangupMsg.addCallCommand("execute");
        hangupMsg.addExecuteAppName("bridge");
        hangupMsg.addExecuteAppArg("loopback/app=voicemail:default ${domain_name} ${dialed_extension}");
        sendMsgList.add(hangupMsg);

        context.handler().sendAsyncMultiSendMsgCommand(context.channel(), sendMsgList);

        // 关闭通道 todo 工作流
        context.closeChannel();
  //      CommandResponse commandResponse = context.sendMessage(bridgeMsg);
  //      log.info("response : {}", commandResponse);

        //同步发送bridge命令接通
//        EslMessage response = context.handler().sendSyncMultiLineCommand(context.channel(), bridgeMsg.getMsgLines());
//        if (response.getHeaderValue(EslHeaders.Name.REPLY_TEXT).startsWith("+OK")) {
//            String originCall = eslEvent.getEventHeaders().get("Caller-Destination-Number");
//            log.info(originCall + " bridge to " + EslEventUtil.getSipToUri(eslEvent) + " successful");
//        } else {
//            log.info("Call bridge failed: " + response.getHeaderValue(EslHeaders.Name.REPLY_TEXT));
//        }
    }

    @Override
    public void handler(Context context, EslEvent eslEvent) {
        log.info("{}", eslEvent);
    }
}
