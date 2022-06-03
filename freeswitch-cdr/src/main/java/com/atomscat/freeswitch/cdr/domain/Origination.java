package com.atomscat.freeswitch.cdr.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Origination class.</p>
 *
 * @author : <a href="everyone@aliyun.com">everyone</a>
 * @version $Id: $Id
 */
@Data
@Accessors(chain = true)
public class Origination {
    private OriginationCallerProfile originationCallerProfile;
}
