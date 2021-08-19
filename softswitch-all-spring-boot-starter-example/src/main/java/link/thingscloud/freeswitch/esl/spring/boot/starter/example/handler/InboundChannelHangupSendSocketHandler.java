package link.thingscloud.freeswitch.esl.spring.boot.starter.example.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import link.thingscloud.freeswitch.esl.InboundClient;
import link.thingscloud.freeswitch.esl.constant.EventNames;
import link.thingscloud.freeswitch.esl.helper.ChannelCacheHelper;
import link.thingscloud.freeswitch.esl.spring.boot.starter.annotation.EslEventName;
import link.thingscloud.freeswitch.esl.spring.boot.starter.handler.EslEventHandler;
import link.thingscloud.freeswitch.esl.transport.CommandResponse;
import link.thingscloud.freeswitch.esl.transport.SendMsg;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;
import link.thingscloud.freeswitch.esl.transport.message.EslMessage;
import link.thingscloud.freeswitch.esl.util.EslEventUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Throwables.propagate;
import static com.google.common.util.concurrent.Futures.getUnchecked;
import static com.google.common.util.concurrent.Uninterruptibles.getUninterruptibly;

@Slf4j
@EslEventName(EventNames.CHANNEL_HANGUP_COMPLETE)
@Component
public class InboundChannelHangupSendSocketHandler implements EslEventHandler {

    private static final String MESSAGE_TERMINATOR = "\n\n";
    private static final String LINE_TERMINATOR = "\n";


    @Override
    public void handle(String address, EslEvent event, String coreUUID) {
        log.info("CHANNEL_HANGUP:  [{}]", JSON.toJSONString(event));
        Channel channel = ChannelCacheHelper.getCache(coreUUID);
        // todo
        // channel.close();
        log.info("CHANNEL_HANGUP: {}", coreUUID);


        try {
            SendMsg sendMsg = new SendMsg(EslEventUtil.getCallerUniqueId(event));
            sendMsg.addCallCommand("execute");
            sendMsg.addExecuteAppName("hangup");
            sendMsg.addEventLock();

            sendApiMultiLineCommand(channel, sendMsg.getMsgLines());

        } catch (Throwable t) {
            throw propagate(t);
        }
    }

    public CompletableFuture<EslMessage> sendApiMultiLineCommand(Channel channel, final List<String> commandLines) {
        //  Build command with double line terminator at the end
        final StringBuilder sb = new StringBuilder();
        for (final String line : commandLines) {
            sb.append(line);
            sb.append(LINE_TERMINATOR);
        }
        sb.append(LINE_TERMINATOR);

        final CompletableFuture<EslMessage> future = new CompletableFuture<>();
        channel.write(sb.toString());
        channel.flush();
        return future;

    }
}
