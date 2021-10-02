package com.atomscat.opensips.event.handler;


public interface IEventApi {

    boolean canSend();

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
