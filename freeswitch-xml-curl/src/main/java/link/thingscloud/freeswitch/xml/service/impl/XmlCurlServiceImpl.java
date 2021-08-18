package link.thingscloud.freeswitch.xml.service.impl;

import com.alibaba.fastjson.JSONObject;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * <p> XmlCurlServiceImpl class.</p>
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

    private List<XmlCurlHandler> xmlCurlHandlers = new ArrayList<>(4);

    private final ExecutorService poolExecutor = new ScheduledThreadPoolExecutor(poolSize,
            new BasicThreadFactory.Builder().namingPattern("pool-executor-%d").daemon(true).build());

    /**
     * {@inheritDoc}
     */
    @Override
    public String handle(HttpServletRequest request) {
        poolExecutor.execute(() -> {
            try {
                handleXmlCurl(request);
            } catch (ParserException e) {
                log.error("handle xml curl failure, cause : ", e);
                log.error("handle curl xml : [{}]", request);
            }
        });
        return "";
    }


    private void handleXmlCurl(HttpServletRequest request) throws ParserException {
        XmlCurl xmlCurl = XmlCurlParser.decodeThenParse(request);
        log.info("handle xml curl : [{}]", JSONObject.toJSONString(xmlCurl));
        xmlCurlHandlers.forEach(xmlCurlHandler -> {
            try {
                xmlCurlHandler.handleXmlCurl(xmlCurl);
            } catch (Throwable e) {
                log.error("freeswitch xml curl handler[{}] handle exception : ", xmlCurlHandler.getClass(), e);
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
            log.info("freeswitch xml curl add Handler : [{}].", handler.getClass());
            xmlCurlHandlers.add(handler);
        }
        if (CollectionUtils.isEmpty(xmlCurlHandlers)) {
            log.warn("freeswitch xml curl Handlers is empty, you can implements Handler to handle xml curl.");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
