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
 * @date 16/12/2021 7:18 下午
 */
@Data
@Accessors(chain = true)
public class Ivr extends Configuration implements Serializable {

    public Ivr() {
        setName(ConfName.IVR.confName);
    }

    @JacksonXmlElementWrapper(localName = "menus", useWrapping = false)
    private List<Menus> menus;
}
