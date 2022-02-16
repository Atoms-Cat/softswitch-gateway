package com.atomscat.owt.server.client.common;

import cn.hutool.core.util.RandomUtil;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


/**
 * @author howell
 * @date 15/2/2022 5:47 PM
 */
public class Signature {
    /**
     *
     * @see [https://github.com/open-webrtc-toolkit/owt-server/blob/master/doc/servermd/RESTAPI.md] * Authentication and Authorization {#RESTAPIsection4}
     * @param username
     * @param role
     * @param serviceid
     * @return
     * @throws Exception
     */
    public static String calculateSignature(String username, String role, String serviceid) throws Exception {
        String toSign = String.valueOf(System.currentTimeMillis()) + ',' + RandomUtil.randomInt(0, 99999);
        if (username !=null && role != null) {
            toSign += ',' + username + ',' + role;
        }
        return Base64.getEncoder().encodeToString(HMACSHA256(toSign, serviceid).getBytes("utf-8"));
    }

    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }
}
