package com.atomscat.spring.boot.common.param;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author th158
 */
@Data
@Accessors(chain = true)
public abstract class BaseElectionProducerParam<T> implements Serializable {
    /**
     * unique primary key
     */
    private String nodeId;

    private T entity;
}
