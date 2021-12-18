package com.atomscat.freeswitch.cdr.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Variables class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Data
@Accessors(chain = true)
public class Variables {
    private Map<String, String> variableTable;

    /**
     * <p>putVariable.</p>
     *
     * @param key   a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
     */
    public void putVariable(String key, String value) {
        if (variableTable == null) {
            variableTable = new ConcurrentHashMap<>(256);
        }
        variableTable.put(key, value);
    }
}
