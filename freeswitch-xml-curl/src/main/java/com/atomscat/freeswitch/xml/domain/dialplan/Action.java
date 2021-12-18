package com.atomscat.freeswitch.xml.domain.dialplan;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author th158
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action implements Serializable {

    @JacksonXmlProperty(localName = "application", isAttribute = true)
    private AppEnum application;

    @JacksonXmlProperty(localName = "data", isAttribute = true)
    private String data;

    public Action(String application, String data) {
        this.application = AppEnum.instance(application);
        this.data = data;
    }

}
