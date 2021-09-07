package com.atomscat.freeswitch.xml.domain.configuration.ivr;

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
public class Entry implements Serializable {

    /**
     * menu-exit - Exit the IVR menues.
     * menu-sub - Load a sub-menu.
     * menu-exec-app - Execute a FreeSWITCH dialplan application.
     * menu-play-sound - Play a sound file or speak text.
     * menu-back - Return to the calling menu level.
     * menu-top - Return to the top level menu.
     * menu-exec-api was previously documented on this page and is documented in the FreeSWITCH book - it is however not currently supported.
     */
    @JacksonXmlProperty(localName = "action", isAttribute = true)
    private String action;

    @JacksonXmlProperty(localName = "digits", isAttribute = true)
    private String digits;

    @JacksonXmlProperty(localName = "param", isAttribute = true)
    private String param;
}
