package com.atomscat.freeswitch.cdr.service.impl;

import com.atomscat.freeswitch.cdr.domain.Cdr;
import com.atomscat.freeswitch.cdr.exception.ParserException;
import com.atomscat.freeswitch.cdr.handler.CdrHandler;
import com.atomscat.freeswitch.cdr.parser.CdrParser;
import com.atomscat.freeswitch.cdr.service.CdrService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * <p>CdrServiceImpl class.</p>
 *
 * @author : <a href="everyone@aliyun.com">everyone</a>
 * @version $Id: $Id
 */
@Slf4j
@Service
public class CdrServiceImpl implements CdrService, ApplicationContextAware, InitializingBean {

    @Value("${cdr.pool.size:8}")
    private int poolSize;
    private final ExecutorService poolExecutor = new ScheduledThreadPoolExecutor(poolSize,
            new BasicThreadFactory.Builder().namingPattern("pool-executor-%d").daemon(true).build());
    private ApplicationContext applicationContext;
    private final List<CdrHandler> cdrHandlers = new ArrayList<>(4);

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(String reqText) {
        poolExecutor.execute(() -> {
            try {
                handleCdr(reqText);
            } catch (ParserException e) {
                log.error("handleCdr failure, cause : ", e);
                log.error("handleCdr xml : [{}]", reqText);
            }
        });
    }


    private void handleCdr(String xml) throws ParserException {
        Cdr cdr = CdrParser.decodeThenParse(xml);
        log.debug("handleCdr cdr : [{}]", cdr);
        cdrHandlers.forEach(cdrHandler -> {
            try {
                cdrHandler.handleCdr(cdr);
            } catch (Throwable e) {
                log.error("freeswitch cdr handler[{}] handle exception : ", cdrHandler.getClass(), e);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPropertiesSet() {
        log.info("freeswitch cdr[{}] start ...", poolSize);
        Map<String, CdrHandler> beansOfType =
                this.applicationContext.getBeansOfType(CdrHandler.class);
        for (CdrHandler handler : beansOfType.values()) {
            log.info("freeswitch cdr add cdrHandler : [{}].", handler.getClass());
            cdrHandlers.add(handler);
        }
        if (CollectionUtils.isEmpty(cdrHandlers)) {
            log.warn("freeswitch cdr cdrHandlers is empty, you can implements CdrHandler to handle cdr.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
