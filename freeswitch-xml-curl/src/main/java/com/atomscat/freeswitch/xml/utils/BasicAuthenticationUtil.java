package com.atomscat.freeswitch.xml.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
        byte[] base64decodedBytes = Base64.getDecoder().decode(str);
        return new String(base64decodedBytes, StandardCharsets.UTF_8);
    }
}
