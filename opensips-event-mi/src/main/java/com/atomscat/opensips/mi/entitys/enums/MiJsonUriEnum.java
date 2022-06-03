package com.atomscat.opensips.mi.entitys.enums;

/**
 * @author : everyone
 * @version 1.0.0
 */
public enum MiJsonUriEnum {

    UL_SHOW_CONTACT("/json/ul_show_contact", "根据sip的AOR地址获取注册在opensips上的信息"),
    EVENT_SUBSCRIBE("/json/event_subscribe", "发送事件订阅地址给opensips");

    private String uri;

    private String desc;

    MiJsonUriEnum(String uri, String desc) {
        this.uri = uri;
        this.desc = desc;
    }


    public String getUri() {
        return uri;
    }

    public String getDesc() {
        return desc;
    }
}
