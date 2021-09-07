package com.atomscat.freeswitch.xml.service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>XmlCurlService interface.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
public interface XmlCurlService {

    /**
     * <p>handle.</p>
     *
     * @param request a {@link HttpServletRequest} object.
     */
    String handle(HttpServletRequest request);

}
