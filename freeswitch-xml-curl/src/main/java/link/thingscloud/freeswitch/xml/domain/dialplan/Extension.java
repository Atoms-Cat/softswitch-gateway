package link.thingscloud.freeswitch.xml.domain.dialplan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author th158
 */
@Data
@XStreamAlias("extension")
public class Extension implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("name")
    private String name;

    @XStreamImplicit(itemFieldName = "condition")
    @XStreamAlias("condition")
    private List<Condition> condition;
}
