package link.thingscloud.freeswitch.esl.spring.boot.starter.example.handler;

import link.thingscloud.freeswitch.esl.outbound.handler.Context;
import link.thingscloud.freeswitch.esl.spring.boot.starter.handler.OutBoundConnectHandler;
import link.thingscloud.freeswitch.esl.spring.boot.starter.handler.OutBoundEventHandler;
import link.thingscloud.freeswitch.esl.transport.CommandResponse;
import link.thingscloud.freeswitch.esl.transport.SendMsg;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;
import link.thingscloud.freeswitch.esl.transport.message.EslHeaders;
import link.thingscloud.freeswitch.esl.transport.message.EslMessage;
import link.thingscloud.freeswitch.esl.util.EslEventUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * fs-outbound 呼入
 */
@Slf4j
@Configuration
public class OutboundConnectPreprocessEslEventHandler implements OutBoundEventHandler, OutBoundConnectHandler {

    public final static String sofia = "sofia/external/";

    @Override
    public void onConnect(Context context, EslEvent eslEvent) {


        SendMsg bridgeMsg = new SendMsg();
        bridgeMsg.addCallCommand("execute");
        bridgeMsg.addExecuteAppName("bridge");
        bridgeMsg.addExecuteAppArg(sofia + EslEventUtil.getSipToUri(eslEvent));

        log.info("bridge to {}", EslEventUtil.getSipToUri(eslEvent));

        // 异步发送bridge命令接通
        // context.handler().sendSyncMultiLineCommand(context.channel(), bridgeMsg.getMsgLines());

        CommandResponse commandResponse = context.sendMessage(bridgeMsg);
        log.info("response : {}", commandResponse);

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
