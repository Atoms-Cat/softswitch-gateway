package com.atomscat.freeswitch.xml.handler;

import com.alibaba.fastjson.JSON;
import com.atomscat.freeswitch.xml.domain.XmlCurl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

/**
 * <p>SimpleCdrHandler class.</p>
 *
 * @author : <a href="everyone@aliyun.com">everyone</a>
 * @version $Id: $Id
 */
@Slf4j
public class SimpleXmlCurlHandler implements XmlCurlHandler, InitializingBean {
    /**
     * {@inheritDoc}
     */
    @Override
    public String handleXmlCurl(XmlCurl xmlCurl) {
        log.info("simple handle cdr : [{}]", JSON.toJSONString(xmlCurl, true));
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPropertiesSet() {
        log.warn("implements 'CdrHandler' can replace SimpleCdrHandler ...");
    }
}
