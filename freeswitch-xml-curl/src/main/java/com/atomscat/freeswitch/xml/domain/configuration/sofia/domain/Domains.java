package com.atomscat.freeswitch.xml.domain.configuration.sofia.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author howell
 * @date 16/12/2021 6:19 下午
 */
@Data
@Accessors(chain = true)
public class Domains implements Serializable {

    @JacksonXmlElementWrapper(localName = "domain", useWrapping = false)
    private List<Domain> domain;
}
