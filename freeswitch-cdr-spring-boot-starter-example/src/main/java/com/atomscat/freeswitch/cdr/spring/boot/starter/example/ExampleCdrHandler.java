package com.atomscat.freeswitch.cdr.spring.boot.starter.example;

import com.alibaba.fastjson.JSON;
import com.atomscat.freeswitch.cdr.domain.Cdr;
import com.atomscat.freeswitch.cdr.handler.CdrHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>ExampleCdrHandler class.</p>
 *
 * @author : <a href="everyone@aliyun.com">everyone</a>
 * @version $Id: $Id
 */
@Slf4j
@Service
public class ExampleCdrHandler implements CdrHandler {
    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCdr(Cdr cdr) {
        log.info("exampel handle cdr : [{}]", JSON.toJSONString(cdr, true));
    }

}
