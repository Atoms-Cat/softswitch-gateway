package com.atomscat.freeswitch.xml.domain.configuration.ivr;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author th158
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Menus implements Serializable {

    @JacksonXmlElementWrapper(localName = "menu", useWrapping = false)
    private List<Menu> menu;
}
