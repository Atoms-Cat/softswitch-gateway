package link.thingscloud.freeswitch.xml.parser;

import com.alibaba.fastjson.JSON;
import link.thingscloud.freeswitch.xml.domain.XmlCurl;
import link.thingscloud.freeswitch.xml.exception.ParserException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * <p>CdrParser class.</p>
 *
 * @author : zhouhailin
 * @version $Id: $Id
 */
@Slf4j
public class XmlCurlParser {



    private XmlCurlParser() {
    }

    /**
     * xml_cdr.conf.xml
     * <p>
     * &lt;param name=&quot;encode&quot; value=&quot;true&quot;/&gt;
     * <p>
     * cdr=&lt;?xml&nbsp;version=&quot;1.0&quot;?&gt;
     * <p>
     * uuid=a_12d714e6-3c49-463a-8965-755b8f598032&amp;cdr=&lt;?xml version=&quot;1.0&quot;?&gt;
     *
     * @param reqText req xml content
     * @return cdr
     * @throws link.thingscloud.freeswitch.cdr.exception.ParserException if any.
     */
    public static XmlCurl decodeThenParse(String reqText) throws ParserException {
        //todo
        return new XmlCurl();
    }
}
