package com.atomscat.freeswitch.xml.handler;


import com.atomscat.freeswitch.xml.domain.XmlCurl;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * <p>XmlCurlHandler interface.</p>
 *
 * @author : <a href="everyone@aliyun.com">everyone</a>
 * @version $Id: $Id
 */
public interface XmlCurlHandler {

    String handleXmlCurl(XmlCurl xmlCurl) throws JsonProcessingException;

}
