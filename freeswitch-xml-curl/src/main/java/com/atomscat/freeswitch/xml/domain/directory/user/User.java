package com.atomscat.freeswitch.xml.domain.directory.user;

import com.atomscat.freeswitch.xml.domain.directory.gateway.Gateways;
import com.atomscat.freeswitch.xml.domain.directory.param.Params;
import com.atomscat.freeswitch.xml.domain.directory.variable.Variables;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {

    @JacksonXmlProperty(localName = "id", isAttribute = true)
    private String id;

    @JacksonXmlProperty(localName = "type", isAttribute = true)
    private String type;

    @JacksonXmlProperty(localName = "params", isAttribute = true)
    private Params params;

    @JacksonXmlProperty(localName = "variables", isAttribute = true)
    private Variables variables;

    @JacksonXmlProperty(localName = "gateways", isAttribute = true)
    private Gateways gateways;

    // todo VCards
}
