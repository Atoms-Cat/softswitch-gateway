package link.thingscloud.freeswitch.esl.spring.boot.starter.example.handler;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import link.thingscloud.freeswitch.cdr.domain.Cdr;
import link.thingscloud.freeswitch.cdr.handler.CdrHandler;
import link.thingscloud.freeswitch.xml.annotation.XmlCurlSectionName;
import link.thingscloud.freeswitch.xml.constant.SectionNames;
import link.thingscloud.freeswitch.xml.domain.XmlCurl;
import link.thingscloud.freeswitch.xml.domain.dialplan.Context;
import link.thingscloud.freeswitch.xml.handler.XmlCurlHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>ExampleCdrHandler class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Slf4j
@Service
@XmlCurlSectionName(SectionNames.DIALPLAN)
public class FsXmlCurlHandler implements XmlCurlHandler {
    /**
     * {@inheritDoc}
     */
    @Override
    public String handleXmlCurl(XmlCurl cdr) {
        Context context = new Context();
        XStream xstream = new XStream();
        context.setName("default");

        log.info(xstream.toXML(context));
        log.info("exampel handle xml curl : [{}]", JSON.toJSONString(cdr, true));
        // todo
        return "<context name=\"default\">\n" +
                        "      <extension name=\"call\">\n" +
                        "        <condition field=\"destination_number\" expression=\"^([0-9]\\d+)$\">\n" +
                        "            <action application=\"answer\"/>\n" +
                        "            <action application=\"sleep\" data=\"2000\"/>\n" +
                        "        </condition>\n" +
                        "      </extension>\n" +
                        "    </context>\n" +
                        "    <context name=\"public\">\n" +
                        "      <extension name=\"call\">\n" +
                        "        <condition field=\"destination_number\" expression=\"^([0-9]\\d+)$\">\n" +
                        "            <action application=\"answer\"/>\n" +
                        "            <action application=\"sleep\" data=\"2000\"/>\n" +
                        "        </condition>\n" +
                        "      </extension>\n" +
                        "    </context>\n" ;
    }

}
