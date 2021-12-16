package com.atomscat.freeswitch.xml.domain.configuration.sofia.gateway;

import com.atomscat.freeswitch.xml.domain.configuration.sofia.Param;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author th158
 */
@Data
@Accessors(chain = true)
@JacksonXmlRootElement(localName = "gateway", namespace = "gateway")
public class Gateway implements Serializable {

    @JacksonXmlProperty(localName = "name", isAttribute = true)
    private String name;

    @JacksonXmlElementWrapper(localName = "param", useWrapping = false)
    private List<Param> param;
}
