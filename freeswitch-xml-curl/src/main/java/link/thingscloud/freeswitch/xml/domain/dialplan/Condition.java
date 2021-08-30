package link.thingscloud.freeswitch.xml.domain.dialplan;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Condition implements Serializable {

    @JacksonXmlProperty(localName = "field", isAttribute = true)
    private String field;

    /**
     * 匹配被叫分机号正则表达式
     */
    @JacksonXmlProperty(localName = "expression", isAttribute = true)
    private String expression;

    @JacksonXmlElementWrapper(localName = "action", useWrapping = false)
    private List<Action> action;
}
