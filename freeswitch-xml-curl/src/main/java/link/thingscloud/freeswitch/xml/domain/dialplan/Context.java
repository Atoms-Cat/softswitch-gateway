package link.thingscloud.freeswitch.xml.domain.dialplan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author th158
 */
@Data
@XStreamAlias("context")
@NoArgsConstructor
@AllArgsConstructor
public class Context implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("name")
    private String name;

    @XStreamAlias("extension")
    @XStreamImplicit(itemFieldName = "extension")
    private List<Extension> extension;
}
