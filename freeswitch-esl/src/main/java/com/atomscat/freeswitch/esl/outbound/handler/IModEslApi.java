package com.atomscat.freeswitch.esl.outbound.handler;


import com.atomscat.freeswitch.esl.transport.CommandResponse;
import com.atomscat.freeswitch.esl.transport.SendMsg;
import com.atomscat.freeswitch.esl.transport.event.EslEvent;
import com.atomscat.freeswitch.esl.transport.message.EslMessage;

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
        /**
         * freeswitch esl 数据格式：文本
         */
        PLAIN("plain"),
        /**
         * freeswitch esl 数据格式：XML
         */
        XML("xml"),
        /**
         * freeswitch esl 数据格式：json
         */
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
        /**
         * 日志类型
         */
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
