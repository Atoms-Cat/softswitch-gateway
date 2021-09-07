package com.atomscat.freeswitch.cdr.handler;

import com.atomscat.freeswitch.cdr.domain.Cdr;

/**
 * <p>CdrHandler interface.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
public interface CdrHandler {

    /**
     * <p>handleCdr.</p>
     *
     * @param cdr a {@link Cdr} object.
     */
    void handleCdr(Cdr cdr);

}
