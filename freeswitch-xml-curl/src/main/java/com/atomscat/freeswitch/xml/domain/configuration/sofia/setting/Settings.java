package com.atomscat.freeswitch.xml.domain.configuration.sofia.setting;

import com.atomscat.freeswitch.xml.domain.configuration.sofia.Param;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author howell
 */
@Data
@Accessors(chain = true)
public class Settings implements Serializable {

    @JacksonXmlElementWrapper(localName = "param", useWrapping = false)
    private List<Param> param;
}
