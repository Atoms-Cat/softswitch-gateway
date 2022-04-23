package com.atomscat.owt.server.client.common;

import cn.hutool.core.util.RandomUtil;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


/**
 * @author howell
 */
public class Signature {
    /*
     * @see [https://github.com/open-webrtc-toolkit/owt-server/blob/master/doc/servermd/RESTAPI.md] * Authentication and Authorization {#RESTAPIsection4}
     */
    public static String getAuthenticate(String username, String role, String serviceid, String servicekey) throws Exception {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String cnonce = String.valueOf(RandomUtil.randomInt(0, 99999));
        String toSign = timestamp + ',' + cnonce;
        if (username != null && role != null) {
            toSign += ',' + username + ',' + role;
        }
        System.out.println(" [Auth] toSign:" + toSign);
        String signature = HMACSHA256(toSign, servicekey);
        System.out.println(" [Auth] signed:" + signature);
        return getAuthenticate(username, role, serviceid, timestamp, cnonce, signature);
    }

    /**
     * MAuth realm=http://webrtc.intel.com,
     * mauth_signature_method=HMAC_SHA256,
     * mauth_username=test,
     * mauth_role=role,
     * mauth_serviceid=53c74879209ee7f96e5cbc9c,
     * mauth_cnonce=87428,
     * mauth_timestamp=1406079112038,
     * mauth_signature=ZjA5NTJlMjE0ZTY4NzhmZWRjZDkxYjNmZjkyOTIxYzMyZjg3NDBjZA==
     */
    public static String getAuthenticate(String username, String role, String serviceid, String timestamp, String cnonce, String signature) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MAuth realm=http://marte3.dit.upm.es").append(",").append("mauth_signature_method=HMAC_SHA256").append(",").append("mauth_username=").append(username).append(",").append("mauth_role=").append(role).append(",").append("mauth_serviceid=").append(serviceid).append(",").append("mauth_cnonce=").append(cnonce).append(",").append("mauth_timestamp=").append(timestamp).append(",").append("mauth_signature=").append(signature);
        return stringBuilder.toString();
    }

    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256_HMAC.init(secretKey);
        byte[] hash = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
        String encodeStr16 = byte2Hex(hash);
        System.out.println(" [Auth] signed16:" + encodeStr16);
        return Base64.getEncoder().encodeToString(encodeStr16.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }


    public static void main(String[] args) {
        try {
            String serviceid = "620cb31f325f0804648ad036";
            String servicekey = "OS3zVCNGScojljrZ2xmk322OF1NSi5sGH/aYgASH9H3repuGxEG/zM/0bFuJP+kQ3hyxZX86tjNo99fHFwhMgk6ne6Tzm7++Rnnucs8NoXljo6oD0dKvyO1BGQxtwe8nvxI5rZDztwdrdQZSGTdG+5dZHHPyqGnNBl4Xp1zNwnI=";
            System.out.println(getAuthenticate("test", "role", serviceid, servicekey));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
