package com.atomscat.freeswitch.xml.domain.configuration.ivr;

import com.atomscat.freeswitch.xml.constant.ConfName;
import com.atomscat.freeswitch.xml.domain.configuration.Configuration;
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
public class Ivr extends Configuration implements Serializable {

    public Ivr() {
        setName(ConfName.IVR.name);
    }

    @JacksonXmlElementWrapper(localName = "menus", useWrapping = false)
    private Menus menus;
}
