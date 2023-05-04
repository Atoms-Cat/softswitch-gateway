package com.atomscat.opensips.mi.entitys.enums;

import lombok.Getter;

/**
 * @author : everyone
 * @version 1.0.0
 */
@Getter
public enum MiJsonUriEnum {

    UL_SHOW_CONTACT("/json/ul_show_contact", "根据sip的AOR地址获取注册在opensips上的信息"),
    EVENT_SUBSCRIBE("/json/event_subscribe", "发送事件订阅地址给opensips");

    private final String uri;

    private final String desc;

    MiJsonUriEnum(String uri, String desc) {
        this.uri = uri;
        this.desc = desc;
    }

}
