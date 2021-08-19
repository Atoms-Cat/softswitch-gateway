package link.thingscloud.freeswitch.esl.spring.boot.starter.example.handler;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import link.thingscloud.freeswitch.xml.annotation.XmlCurlSectionName;
import link.thingscloud.freeswitch.xml.constant.SectionNames;
import link.thingscloud.freeswitch.xml.domain.XmlCurl;
import link.thingscloud.freeswitch.xml.domain.dialplan.Action;
import link.thingscloud.freeswitch.xml.domain.dialplan.Condition;
import link.thingscloud.freeswitch.xml.domain.dialplan.Context;
import link.thingscloud.freeswitch.xml.domain.dialplan.Extension;
import link.thingscloud.freeswitch.xml.handler.XmlCurlHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        Context publicContext = new Context();
        publicContext.setName("public");

        Context defaultContext = new Context();
        defaultContext.setName("default");

        List<Extension> extensionlist = new ArrayList<>();

        Extension extension = new Extension();
        extension.setName("call");


        List<Condition> conditionList = new ArrayList<>();

        Condition condition = new Condition();
        condition.setField("destination_number");
        condition.setExpression("^([0-9]\\d+)$");

        List<Action> actionList = new ArrayList<>();
        actionList.add(new Action("answer",null));
        actionList.add(new Action("sleep","2000"));
        condition.setAction(actionList);

        conditionList.add(condition);
        extension.setCondition(conditionList);

        extensionlist.add(extension);
        publicContext.setExtension(extensionlist);

        defaultContext.setExtension(extensionlist);

        ObjectMapper xmlMapper = new XmlMapper();
        String xml = "";
        try {
            xml += xmlMapper.writeValueAsString(defaultContext);
            xml += xmlMapper.writeValueAsString(publicContext);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        log.info(xml);
        log.info("exampel handle xml curl : [{}]", JSON.toJSONString(cdr, true));
        return xml;
        // todo

        /**
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
                "    </context>\n";
         **/
    }




}
