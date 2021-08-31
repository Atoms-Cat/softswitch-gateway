package link.thingscloud.freeswitch.xml.domain.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author th158
 */
@Data
@JacksonXmlRootElement(localName = "configuration")
public class Configuration<T> implements Serializable {

    /**
     * SectionNames.Configuration
     *   - ivr.conf
     */
    @JacksonXmlProperty(localName = "name", isAttribute = true)
    private String name;

    @JacksonXmlProperty(localName = "description", isAttribute = true)
    private String description;

    @JacksonXmlElementWrapper(localName = "list", useWrapping = false)
    private List<T> list;

    public String toXmlString(String name) throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        return xmlMapper.writeValueAsString(this).replaceAll("list", name);
    }
}
