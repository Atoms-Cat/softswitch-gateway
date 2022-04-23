package com.atomscat.freeswitch.xml.domain.configuration.sofia;

import com.atomscat.freeswitch.xml.constant.ConfName;
import com.atomscat.freeswitch.xml.domain.configuration.Configuration;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.profile.Profiles;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author howell
 */
@Data
@Accessors(chain = true)
public class Sofia extends Configuration implements Serializable {

    public Sofia() {
        setName(ConfName.SOFIA.confName);
    }

    @JacksonXmlProperty(localName = "global_settings", isAttribute = true)
    private GlobalSettings globalSettings;

    /**
     * This is a sofia sip profile/user agent.  This will service exactly one
     * ip and port.  In FreeSWITCH you can run multiple sip user agents on
     * their own ip and port.
     */
    @JacksonXmlProperty(localName = "profiles", isAttribute = true)
    private Profiles profiles;

}
