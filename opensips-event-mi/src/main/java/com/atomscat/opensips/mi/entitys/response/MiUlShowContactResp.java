package com.atomscat.opensips.mi.entitys.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.atomscat.opensips.mi.entitys.MiJsonError;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author : zhouhailin
 * @version 1.0.0
 */
@Data
public class MiUlShowContactResp implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<AorItem> aor;

    private MiJsonError error;

    @Data
    static class AorItem {

        private Children children;

        private String value;
    }

    @Data
    static class Children {
        @JSONField(name = "Contact")
        private List<ContactItem> contact;
    }

    @Data
    static class ContactItem {
        private Attributes attributes;

        private ContactChildren children;

        private String value;
    }

    @Data
    static class Attributes {
        @JSONField(name = "Q")
        private String q;
    }

    @Data
    static class ContactChildren {
        @JSONField(name = "Attr")
        private String attr;

        @JSONField(name = "Callid")
        private String callId;

        @JSONField(name = "Cflags")
        private String cflags;

        @JSONField(name = "ContactID")
        private String contactID;

        @JSONField(name = "Cseq")
        private String cseq;

        @JSONField(name = "Expires")
        private String expires;

        @JSONField(name = "Flags")
        private String flags;

        @JSONField(name = "Methods")
        private String methods;

        @JSONField(name = "Received")
        private String received;

        @JSONField(name = "Socket")
        private String socket;

        @JSONField(name = "State")
        private String state;

        @JSONField(name = "User-agent")
        private String userAgent;
    }

}
