package com.atomscat.opensips.mi.entitys.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author : zhouhailin
 * @version 1.0.0
 */
@Data
public class MiEventSubscribeReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @JSONField(ordinal = 1)
    private String eventName;

    @JSONField(ordinal = 2)
    private String externalApplicationSocket;

    /**
     * in seconds - if absent, the subscription is valid only one hour (3600 s)
     */
    @JSONField(ordinal = 3)
    private String expireTime;

}
