package com.atomscat.opensips.mi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atomscat.opensips.mi.entitys.NetString;
import com.atomscat.opensips.mi.entitys.enums.MiJsonUriEnum;
import com.atomscat.opensips.mi.entitys.request.MiUlShowContactReq;
import com.atomscat.opensips.mi.entitys.response.MiUlShowContactResp;
import com.atomscat.opensips.mi.service.MiUlShowContactService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


/**
 * @author : everyone
 * @version 1.0.0
 */
@Slf4j
public class MiUlShowContactServiceImpl implements MiUlShowContactService {

    /**
     * @param urlBase OpenSips MI Service address base url
     * @param ulShowContactReq a {@link MiUlShowContactReq} request param
     * @return {@link MiUlShowContactResp}
     */
    @Override
    public MiUlShowContactResp getUlShowContact(String urlBase, MiUlShowContactReq ulShowContactReq) {
        MiUlShowContactResp ulShowContactResp = new MiUlShowContactResp();
        try {
            // 组装请求参数
            StringBuilder params = new StringBuilder();
            params.append(NetString.MI_JSON_PARAMS);
            params.append(ulShowContactReq.getTableName()).append(NetString.COMMA);
            params.append(ulShowContactReq.getAor()).append(NetString.COMMA);
            // 发起请求
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(urlBase + MiJsonUriEnum.UL_SHOW_CONTACT.getUri() + params)
                    .build();
            Response response = client.newCall(request).execute();
            ulShowContactResp = JSONObject.parseObject(response.body().string(), MiUlShowContactResp.class);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Request Opensips MI service [Ul Show Contact] ERROR:", e);
        }
        return ulShowContactResp;
    }
}
