package com.atomscat.opensips.mi.entitys;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : everyone
 * @version 1.0.0
 */
@Data
public class MiJsonError implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;

    private String message;
}
