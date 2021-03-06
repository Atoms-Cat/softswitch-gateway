package com.atomscat.freeswitch.xml.domain.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.io.Serializable;

/**
 * @author th158
 */
@Data
@JacksonXmlRootElement(localName = "configuration")
public class Configuration implements Serializable {

    /**
     * {@link com.atomscat.freeswitch.xml.constant.SectionNames.Configuration}
     * - ivr.conf
     */
    @JacksonXmlProperty(localName = "name", isAttribute = true)
    private String name;

    @JacksonXmlProperty(localName = "description", isAttribute = true)
    private String description;

    public String toXmlString() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        return xmlMapper.writeValueAsString(this);
    }
}
