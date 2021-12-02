package com.atomscat.opensips.mi.entitys.enums;

/**
 *
 * @author : zhouhailin
 * @version 1.0.0
 */
public enum MiJsonUriEnum {

    UL_SHOW_CONTACT("/json/ul_show_contact", "根据sip的AOR地址获取注册在opensips上的信息"),
    EVENT_SUBSCRIBE("/json/event_subscribe", "发送事件订阅地址给opensips")

    ;

    private String uri;

    private String msg;

    MiJsonUriEnum(String uri, String msg) {
        this.uri = uri;
        this.msg = msg;
    }


    public String getUri() {
        return uri;
    }

    public String getMsg() {
        return msg;
    }
}
