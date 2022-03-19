package com.atomscat.spring.boot.common.param;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author th158
 */
@Data
@Accessors(chain = true)
public class ElectionResultParam<K, V> implements Serializable {

    private K key;

    private List<V> valueList;
}
