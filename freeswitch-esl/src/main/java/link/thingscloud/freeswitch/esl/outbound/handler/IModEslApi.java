package link.thingscloud.freeswitch.esl.outbound.handler;


import link.thingscloud.freeswitch.esl.transport.CommandResponse;
import link.thingscloud.freeswitch.esl.transport.SendMsg;
import link.thingscloud.freeswitch.esl.transport.event.EslEvent;
import link.thingscloud.freeswitch.esl.transport.message.EslMessage;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IModEslApi {

    boolean canSend();

    EslMessage sendApiCommand(String command, String arg);

    CompletableFuture<EslEvent> sendBackgroundApiCommand(String command, String arg);

    CommandResponse setEventSubscriptions(EventFormat format, String events);

    CommandResponse cancelEventSubscriptions();

    CommandResponse addEventFilter(String eventHeader, String valueToFilter);

    CommandResponse deleteEventFilter(String eventHeader, String valueToFilter);

    CommandResponse sendMessage(SendMsg sendMsg);

    CommandResponse sendMessage(List<SendMsg> sendMsgList);

    CommandResponse sendMessage(SendMsg sendMsg, long timeout);

    CommandResponse setLoggingLevel(LoggingLevel level);

    CommandResponse cancelLogging();

    enum EventFormat {

        PLAIN("plain"),
        XML("xml"),
        JSON("json");

        private final String text;

        EventFormat(String txt) {
            this.text = txt;
        }

        @Override
        public String toString() {
            return text;
        }

    }

    enum LoggingLevel {

        CONSOLE("console"),
        DEBUG("debug"),
        INFO("info"),
        NOTICE("notice"),
        WARNING("warning"),
        ERR("err"),
        CRIT("crit"),
        ALERT("alert");

        private final String text;

        LoggingLevel(String txt) {
            this.text = txt;
        }

        @Override
        public String toString() {
            return text;
        }

    }
}
