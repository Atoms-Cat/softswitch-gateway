package link.thingscloud.freeswitch.xml.domain.dialplan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@XStreamAlias("condition")
public class Condition implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("field")
    private String field;

    @XStreamAsAttribute
    @XStreamAlias("expression")
    private String expression;

    @XStreamImplicit(itemFieldName = "action")
    @XStreamAlias("action")
    private List<Action> action;
}
