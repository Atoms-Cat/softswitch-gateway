package link.thingscloud.freeswitch.xml.handler;


import link.thingscloud.freeswitch.xml.domain.XmlCurl;

/**
 * <p>CdrHandler interface.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
public interface XmlCurlHandler {

    /**
     * <p>handleCdr.</p>
     *
     * @param cdr a {@link XmlCurl} object.
     */
    String handleXmlCurl(XmlCurl cdr);

}
