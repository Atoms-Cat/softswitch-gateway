package com.atomscat.freeswitch.xml.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atomscat.freeswitch.xml.annotation.XmlCurlSectionName;
import com.atomscat.freeswitch.xml.domain.XmlCurl;
import com.atomscat.freeswitch.xml.handler.XmlCurlHandler;
import com.atomscat.freeswitch.xml.parser.XmlCurlParser;
import com.atomscat.freeswitch.xml.service.XmlCurlService;
import com.atomscat.freeswitch.xml.exception.ParserException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p> XmlCurlServiceImpl class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Slf4j
@Service
public class XmlCurlServiceImpl implements XmlCurlService, InitializingBean {


    @Autowired
    private final List<XmlCurlHandler> xmlCurlHandlers = Collections.emptyList();
    private final Map<String, List<XmlCurlHandler>> handlerTable = new HashMap<>(16);

    /**
     * {@inheritDoc}
     */
    @Override
    public String handle(HttpServletRequest request) {
        try {
            return handleXmlCurl(request);
        } catch (ParserException e) {
            log.error("handle xml curl failure, cause : ", e);
            log.error("handle curl xml : [{}]", request);
        }
        // todo
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<document type=\"freeswitch/xml\">\n" +
                "  <section name=\"result\">\n" +
                "    <result status=\"not found\" />\n" +
                "  </section>\n" +
                "</document>";
    }


    private String handleXmlCurl(HttpServletRequest request) throws ParserException {
        XmlCurl xmlCurl = XmlCurlParser.decodeThenParse(request);
        log.info("handle xml curl : [{}]", JSONObject.toJSONString(xmlCurl));
        // 获取事件名称
        String section = xmlCurl.getSection();
        if (StringUtils.isBlank(section)) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        List<XmlCurlHandler> handlers = handlerTable.get(section.toUpperCase(Locale.ROOT));
        if (!CollectionUtils.isEmpty(handlers)) {
            stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<document type=\"freeswitch/xml\">");
            stringBuilder.append("<section name=\"" + section + "\">");
            handlers.forEach(xmlCurlHandler -> {
                try {
                    // section 为 configuration时， 判断 key_value 类型执行对应的Handler
                    if (hasKey(xmlCurlHandler, xmlCurl.getKeyValue())) {
                        stringBuilder.append(xmlCurlHandler.handleXmlCurl(xmlCurl));
                    }
                } catch (Throwable e) {
                    log.error("freeswitch xml curl handler[{}] handle exception : ", xmlCurlHandler.getClass(), e);
                }
            });
            stringBuilder.append("</section>\n" +
                    "</document>");
        }
        return stringBuilder.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPropertiesSet() {
        log.info("freeswitch xml curl add Handler ...");
        for (XmlCurlHandler eventHandler : xmlCurlHandlers) {
            XmlCurlSectionName eventName = eventHandler.getClass().getAnnotation(XmlCurlSectionName.class);
            if (eventName == null) {
                // FIXED : AOP
                eventName = eventHandler.getClass().getSuperclass().getAnnotation(XmlCurlSectionName.class);
            }
            if (eventName == null || ArrayUtils.isEmpty(eventName.value())) {
                continue;
            }
            for (String value : eventName.value()) {
                if (StringUtils.isBlank(value)) {
                    continue;
                }
                log.info("freeswitch xml curl add Handler : [{}].", eventHandler.getClass());
                handlerTable.computeIfAbsent(value, k -> new ArrayList<>(4)).add(eventHandler);
            }
        }

        if (CollectionUtils.isEmpty(xmlCurlHandlers)) {
            log.warn("freeswitch xml curl Handlers is empty, you can implements Handler to handle xml curl.");
        }
    }

    protected boolean hasKey(XmlCurlHandler eventHandler, String keyValue) {
        XmlCurlSectionName eventName = eventHandler.getClass().getAnnotation(XmlCurlSectionName.class);
        if (eventName == null) {
            // FIXED : AOP
            eventName = eventHandler.getClass().getSuperclass().getAnnotation(XmlCurlSectionName.class);
        }
        if (eventName == null || ArrayUtils.isEmpty(eventName.value())) {
            return false;
        }
        if (StringUtils.isBlank(eventName.key())) {
            return true;
        } else if (StringUtils.isNotBlank(keyValue) && keyValue.equals(eventName.key())) {
            return true;
        } else {
            return false;
        }
    }

}
