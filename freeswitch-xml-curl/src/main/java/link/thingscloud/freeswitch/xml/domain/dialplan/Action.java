package link.thingscloud.freeswitch.xml.domain.dialplan;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

import java.io.Serializable;

@Data
@XStreamAlias("action")
public class Action implements Serializable {

    @XStreamAsAttribute
    @XStreamAlias("application")
    private String application;

    @XStreamAsAttribute
    @XStreamAlias("data")
    private String data;
}
