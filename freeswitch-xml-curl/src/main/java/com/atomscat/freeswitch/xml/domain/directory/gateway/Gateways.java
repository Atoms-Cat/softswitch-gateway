package com.atomscat.freeswitch.xml.domain.directory.gateway;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Gateways implements Serializable {
    @JacksonXmlElementWrapper(localName = "gateway", useWrapping = false)
    private List<Gateway> gateway;
}
