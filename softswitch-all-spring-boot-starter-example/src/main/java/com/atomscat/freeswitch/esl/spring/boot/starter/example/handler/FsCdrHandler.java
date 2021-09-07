package com.atomscat.freeswitch.esl.spring.boot.starter.example.handler;

import com.alibaba.fastjson.JSON;
import com.atomscat.freeswitch.cdr.domain.Cdr;
import com.atomscat.freeswitch.cdr.handler.CdrHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>ExampleCdrHandler class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Slf4j
@Service
public class FsCdrHandler implements CdrHandler {
    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCdr(Cdr cdr) {
        log.info("exampel handle cdr : [{}]", JSON.toJSONString(cdr, true));
    }

}
