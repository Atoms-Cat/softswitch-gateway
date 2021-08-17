package link.thingscloud.freeswitch.xml.service.impl;

import link.thingscloud.freeswitch.xml.domain.XmlCurl;
import link.thingscloud.freeswitch.xml.exception.ParserException;
import link.thingscloud.freeswitch.xml.handler.XmlCurlHandler;
import link.thingscloud.freeswitch.xml.parser.XmlCurlParser;
import link.thingscloud.freeswitch.xml.service.XmlCurlService;
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
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Slf4j
@Service
public class XmlCurlServiceImpl implements XmlCurlService, ApplicationContextAware, InitializingBean {

    @Value("${xml curl.pool.size:8}")
    private int poolSize;

    private ApplicationContext applicationContext;

    private List<XmlCurlHandler> cdrHandlers = new ArrayList<>(4);

    private final ExecutorService poolExecutor = new ScheduledThreadPoolExecutor(poolSize,
            new BasicThreadFactory.Builder().namingPattern("pool-executor-%d").daemon(true).build());

    /**
     * {@inheritDoc}
     */
    @Override
    public String handle(String reqText) {
        poolExecutor.execute(() -> {
            try {
                handleCdr(reqText);
            } catch (ParserException e) {
                log.error("handleCdr failure, cause : ", e);
                log.error("handleCdr xml : [{}]", reqText);
            }
        });
        return "";
    }


    private void handleCdr(String xml) throws ParserException {
        XmlCurl xmlCurl = XmlCurlParser.decodeThenParse(xml);
        log.debug("handleCdr xml curl : [{}]", xmlCurl);
        cdrHandlers.forEach(cdrHandler -> {
            try {
                cdrHandler.handleXmlCurl(xmlCurl);
            } catch (Throwable e) {
                log.error("freeswitch xml curl handler[{}] handle exception : ", cdrHandler.getClass(), e);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPropertiesSet() {
        log.info("freeswitch xml curl[{}] start ...", poolSize);
        Map<String, XmlCurlHandler> beansOfType =
                this.applicationContext.getBeansOfType(XmlCurlHandler.class);
        for (XmlCurlHandler handler : beansOfType.values()) {
            log.info("freeswitch xml curl add cdrHandler : [{}].", handler.getClass());
            cdrHandlers.add(handler);
        }
        if (CollectionUtils.isEmpty(cdrHandlers)) {
            log.warn("freeswitch xml curl cdrHandlers is empty, you can implements CdrHandler to handle xml curl.");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
