package com.atomscat.freeswitch.xml.domain.configuration.sofia.profile;

import com.atomscat.freeswitch.xml.domain.configuration.sofia.aliase.Aliases;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.domain.Domains;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.gateway.Gateways;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.setting.Settings;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author howell
 */
@Data
@Accessors(chain = true)
public class Profile implements Serializable {
    @JacksonXmlProperty(localName = "name", isAttribute = true)
    private String name;

    @JacksonXmlProperty(localName = "gateways", isAttribute = true)
    private Gateways gateways;

    @JacksonXmlProperty(localName = "aliases", isAttribute = true)
    private Aliases aliases;

    @JacksonXmlProperty(localName = "domains", isAttribute = true)
    private Domains domains;

    @JacksonXmlProperty(localName = "settings", isAttribute = true)
    private Settings settings;
}
