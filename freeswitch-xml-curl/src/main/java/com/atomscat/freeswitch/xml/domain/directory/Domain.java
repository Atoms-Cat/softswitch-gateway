package com.atomscat.freeswitch.xml.domain.directory;

import com.atomscat.freeswitch.xml.domain.directory.group.Groups;
import com.atomscat.freeswitch.xml.domain.directory.param.Params;
import com.atomscat.freeswitch.xml.domain.directory.variable.Variables;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.io.Serializable;

@Data
@JacksonXmlRootElement(localName = "domain")
public class Domain implements Serializable {
    @JacksonXmlProperty(localName = "name", isAttribute = true)
    private String name;

    @JacksonXmlProperty(localName = "params", isAttribute = true)
    private Params params;

    @JacksonXmlProperty(localName = "variables", isAttribute = true)
    private Variables variables;

    @JacksonXmlProperty(localName = "groups", isAttribute = true)
    private Groups groups;

    public String toXmlString() throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        return xmlMapper.writeValueAsString(this);
    }
}
