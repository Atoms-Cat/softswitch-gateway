package com.atomscat.freeswitch.xml.domain.configuration.gateway;

import com.atomscat.freeswitch.xml.domain.configuration.ivr.Menu;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author th158
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gateways implements Serializable {
    @JacksonXmlElementWrapper(localName = "gateway", useWrapping = false)
    private List<Gateway> gateway;
}
