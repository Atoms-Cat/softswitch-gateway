package com.atomscat.freeswitch.xml.domain.configuration.sofia.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/*
 * indicator to parse the directory for domains with parse="true" to
 * get gateways
 * <domain name="$${domain}" parse="true"/>
 * indicator to parse the directory for domains with parse="true" to
 * get gateways and alias every domain to this profile
 * This may seem like just a configuration convenience, but
 * aliasing has real effects on the handling of packets.  If
 * you're sharing a profile between multiple realms, you
 * want to alias all realms to the shared profile.
 */
/**
 * @author howell
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Domain implements Serializable {

    @JacksonXmlProperty(localName = "name", isAttribute = true)
    private String name;

    @JacksonXmlProperty(localName = "alias", isAttribute = true)
    private String alias;

    @JacksonXmlProperty(localName = "parse", isAttribute = true)
    private String parse;
}
