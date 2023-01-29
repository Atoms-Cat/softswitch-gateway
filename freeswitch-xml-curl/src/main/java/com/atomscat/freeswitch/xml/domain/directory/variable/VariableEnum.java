package com.atomscat.freeswitch.xml.domain.directory.variable;

public enum VariableEnum {
    /**
     * domain
     */
    RECORD_STEREO("record_stereo", ""),

    DEFAULT_GATEWAY("default_gateway", ""),

    DEFAULT_AREACODE("default_areacode", ""),

    TRANSFER_FALLBACK_EXTENSION("transfer_fallback_extension", ""),

    /**
     * user
     */
    TOLL_ALLOW("toll_allow", ""),

    ACCOUNTCODE("accountcode", ""),

    USER_CONTEXT("user_context", ""),

    EFFECTIVE_CALLER_ID_NAME("effective_caller_id_name", ""),

    EFFECTIVE_CALLER_ID_NUMBER("effective_caller_id_number", ""),

    OUTBOUND_CALLER_ID_NAME("outbound_caller_id_name", ""),

    OUTBOUND_CALLER_ID_NUMBER("outbound_caller_id_number", ""),

    CALLGROUP("callgroup", ""),
    ;

    /**
     * 配置项值
     */
    public String name;

    /**
     * 配置项说明
     */
    public String msg;

    VariableEnum(String name, String msg) {
        this.name = name;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public String getName() {
        return name;
    }
}
