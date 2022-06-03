package com.atomscat.freeswitch.cdr.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Inbound class.</p>
 *
 * @author : <a href="everyone@aliyun.com">everyone</a>
 * @version $Id: $Id
 */
@Data
@Accessors(chain = true)
public class Inbound {
    private String rawBytes;
    private String mediaBytes;
    private String packetCount;
    private String mediaPacketCount;
    private String skipPacketCount;
    private String jitterPacketCount;
    private String dtmfPacketCount;
    private String cngPacketCount;
    private String flushPacketCount;
    private String largestJbSize;
    private String jitterMinVariance;
    private String jitterMaxVariance;
    private String jitterLossRate;
    private String jitterBurstRate;
    private String meanInterval;
    private String flawTotal;
    private String qualityPercentage;
    private String mos;
}
