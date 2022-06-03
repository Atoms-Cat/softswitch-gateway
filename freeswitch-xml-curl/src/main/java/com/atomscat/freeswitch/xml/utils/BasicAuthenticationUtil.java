package com.atomscat.freeswitch.xml.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Base64;

/**
 * @author th158
 */
@Slf4j
public class BasicAuthenticationUtil {
    @SuppressWarnings("restriction")
    public static String base64Decode(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String s = null;
        try {
            byte[] base64decodedBytes = Base64.getDecoder().decode(str);
            s = new String(base64decodedBytes, "utf-8");
        }  catch (IOException e) {
            log.error("base64Decode error", e);
        }
        return s;
    }
}
