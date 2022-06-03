package com.atomscat.opensips.mi.entitys.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : everyone
 * @version 1.0.0
 */
@Data
public class MiUlShowContactReq implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * table where the AOR resides (Ex: location).
     */
    @JSONField(ordinal = 1)
    private String tableName;

    /**
     * user AOR in username[@domain] format (domain must be supplied only if use_domain option is on).
     */
    @JSONField(ordinal = 2)
    private String aor;
}
