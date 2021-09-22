package com.atomscat.freeswitch.xml.domain.configuration.gateway;

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
public class Param implements Serializable {

    /**
     * register true or false (like when you don't want to register but want to auth to a gateway)
     * schema (Currently should be left blank which is Digest by default)
     * realm (authorization realm in some cases you might need to set this)
     * username (this is the authorization and from username in the registration unless from-user is set.)
     * auth-username (this will be the authentication username for an outbound registration)
     * password (well the password)
     * caller-id-in-from (for those times the callerid is in the from for when the remote doesn't do rpid or p-asserted)
     * extension (this is the contact extension for the remote to contact)
     * extension-in-contact (this will send the extension in the SIP Contact header)
     * proxy (defaults to the gateway name unless you set this param)
     * context (what context this gateway is in)
     * expire-seconds (how many seconds to register for default is 3600 if left empty)
     * retry-seconds (this sets the number of seconds to retry the registration)
     * from-user (the username used in the from field defaults to the username if left empty)
     * from-domain (the domain to use in the from field)
     * register-proxy (for those times you have a proxy you register with and a different proxy to send calls to defaults to gateway name or proxy if left empty)
     * contact-params (extra contact params ie tport=tcp)
     * register-transport (udp,tcp or tls if you have it enabled)
     */
    @JacksonXmlProperty(localName = "name", isAttribute = true)
    private String name;

    @JacksonXmlProperty(localName = "value", isAttribute = true)
    private String value;
}
