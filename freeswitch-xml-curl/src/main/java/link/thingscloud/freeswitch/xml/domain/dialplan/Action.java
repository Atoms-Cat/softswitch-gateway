package link.thingscloud.freeswitch.xml.domain.dialplan;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action implements Serializable {

    @JacksonXmlProperty(localName = "application", isAttribute = true)
    private String application;

    @JacksonXmlProperty(localName = "data", isAttribute = true)
    private String data;
}
