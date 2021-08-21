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
public class FsDialplanXmlCurlHandler implements XmlCurlHandler {
    /**
     * {@inheritDoc}
     */
    @Override
    public String handleXmlCurl(XmlCurl cdr) {
        String xml = "";
        try {
            xml += getContext("public");
            xml += getContext("default");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info(xml);
        log.debug("exampel handle xml curl : [{}]", JSON.toJSONString(cdr, true));
        return xml;
    }

    private String getContext(String name) throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        Context context = new Context();
        context.setName(name);
        // todo
        context.setExtension(getExtension());
        return xmlMapper.writeValueAsString(context);
    }


    private List<Extension> getExtension() {
        List<Extension> extensionlist = new ArrayList<>();

        Extension extension = new Extension();
        extension.setName("call");
        // todo
        extension.setCondition(getCondition());

        extensionlist.add(extension);
        return extensionlist;
    }

    private List<Condition> getCondition() {
        List<Condition> conditionList = new ArrayList<>();

        Condition condition = new Condition();
        condition.setField("destination_number");
        condition.setExpression("^([0-9]\\d+)$");
        // todo
        condition.setAction(getAction());

        conditionList.add(condition);
        return conditionList;
    }

    private List<Action> getAction() {
        List<Action> actionList = new ArrayList<>();
        // todo
        actionList.add(new Action("answer", null));
        actionList.add(new Action("sleep", "60000"));
        return actionList;
    }


}
