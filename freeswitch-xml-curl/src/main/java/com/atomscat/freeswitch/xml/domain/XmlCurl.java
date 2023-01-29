package com.atomscat.freeswitch.xml.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

@Data
@Accessors(chain = true)
public class XmlCurl implements Serializable {

    String hostname;

    String section;

    @JSONField(name = "tag_name")
    String tagName;

    @JSONField(name = "key_name")
    String keyName;

    @JSONField(name = "key_value")
    String keyValue;

    @JSONField(name = "Event-Name")
    String eventName;

    @JSONField(name = "Core-UUID")
    String coreUuid;

    @JSONField(name = "FreeSWITCH-Hostname")
    String freeSwitchHostName;

    @JSONField(name = "FreeSWITCH-Switchname")
    String freeSwitchSwitchName;

    @JSONField(name = "FreeSWITCH-IPv4")
    String freeSwitchIPv4;

    @JSONField(name = "FreeSWITCH-IPv6")
    String freeSwitchIPv6;

    Map<String, String> metadata;

}
