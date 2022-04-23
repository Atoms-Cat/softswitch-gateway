package com.atomscat.freeswitch.xml.domain.configuration.sofia.aliase;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author howell
 */
@Data
@Accessors(chain = true)
public class Alias implements Serializable {
    /**
     * aliases are other names that will work as a valid profile name for this profile
     */
    @JacksonXmlProperty(localName = "name", isAttribute = true)
    private String name;
}
