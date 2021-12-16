package com.atomscat.freeswitch.xml.domain.configuration.sofia.aliase;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author howell
 * @date 16/12/2021 6:15 下午
 */
@Data
@Accessors(chain = true)
public class Aliases implements Serializable {
    @JacksonXmlElementWrapper(localName = "alias", useWrapping = false)
    private List<Alias> alias;
}
