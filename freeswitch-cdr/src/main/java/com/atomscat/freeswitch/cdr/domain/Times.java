package com.atomscat.freeswitch.cdr.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Times class.</p>
 *
 * @author : <a href="everyone@aliyun.com">everyone</a>
 * @version $Id: $Id
 */
@Data
@Accessors(chain = true)
public class Times {
    private Long createdTime;
    private Long profileCreatedTime;
    private Long progressTime;
    private Long progressMediaTime;
    private Long answeredTime;
    private Long bridgedTime;
    private Long lastHoldTime;
    private Long holdAccumTime;
    private Long hangupTime;
    private Long resurrectTime;
    private Long transferTime;
}
