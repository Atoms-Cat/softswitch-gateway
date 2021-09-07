package com.atomscat.freeswitch.xml.handler;


import com.atomscat.freeswitch.xml.domain.XmlCurl;

/**
 * <p>XmlCurlHandler interface.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
public interface XmlCurlHandler {

    /**
     * <p>xmlCurl.</p>
     *
     * @param xmlCurl a {@link XmlCurl} object.
     */
    String handleXmlCurl(XmlCurl xmlCurl);

}
