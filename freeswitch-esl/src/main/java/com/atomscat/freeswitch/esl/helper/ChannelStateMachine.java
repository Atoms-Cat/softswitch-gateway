package com.atomscat.freeswitch.esl.helper;

import lombok.Data;

import java.io.Serializable;

/**
 * @author th158
 */
@Data
public class ChannelStateMachine implements Serializable {

    /**
     * inbound or outbound
     */
    private String mode;

    /**
     * inbound mode: fs channel address
     */
    private String address;

    /**
     * inbound mode: fs channel caller unique ID
     * outbound mode:
     */
    private String callerUniqueID;

    /**
     * outbound mode: A certain one address in HA app
     */
    private String localChannelAddress;

    /**
     * ESL event name
     */
    private String eventName;

    /**
     * core UUID
     */
    private String coreUUID;


}
