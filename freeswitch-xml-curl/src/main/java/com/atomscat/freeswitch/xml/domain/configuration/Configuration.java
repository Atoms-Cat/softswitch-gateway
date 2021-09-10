package com.atomscat.freeswitch.xml.domain.configuration;

import com.atomscat.freeswitch.xml.constant.ConfName;
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
     * {@link com.atomscat.freeswitch.xml.constant.SectionNames.Configuration}
     *   - ivr.conf
     */
    @JacksonXmlProperty(localName = "name", isAttribute = true)
    private String name;

    @JacksonXmlProperty(localName = "description", isAttribute = true)
    private String description;

    @JacksonXmlElementWrapper(localName = "list", useWrapping = false)
    private List<T> list;

    /**
     * obj to xml string
     * @param name a {@link ConfName} enum val
     * @return xml string
     * @throws JsonProcessingException
     */
    public String toXmlString(String name) throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        return xmlMapper.writeValueAsString(this).replaceAll("list", name);
    }
}
