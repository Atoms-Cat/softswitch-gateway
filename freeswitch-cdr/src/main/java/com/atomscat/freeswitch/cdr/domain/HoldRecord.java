package com.atomscat.freeswitch.cdr.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>HoldRecord class.</p>
 *
 * @author : <a href="everyone@aliyun.com">everyone</a>
 * @version $Id: $Id
 */
@Data
@Accessors(chain = true)
public class HoldRecord {
    private List<Hold> holds;
}
