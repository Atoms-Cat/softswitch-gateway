package com.atomscat.opensips.mi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atomscat.opensips.mi.entitys.NetString;
import com.atomscat.opensips.mi.entitys.enums.MiJsonUriEnum;
import com.atomscat.opensips.mi.entitys.request.MiEventSubscribeReq;
import com.atomscat.opensips.mi.entitys.response.MiEventSubscribeResp;
import com.atomscat.opensips.mi.service.MiEventSubscribeService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


/**
 * @author : zhouhailin
 * @version 1.0.0
 */
@Slf4j
public class MiEventSubscribeServiceImpl implements MiEventSubscribeService {

    @Override
    public MiEventSubscribeResp setEventSubscribe(String urlBase, MiEventSubscribeReq eventSubscribeReq) {
        MiEventSubscribeResp eventSubscribeResp = new MiEventSubscribeResp();
        try {
            // 组装请求参数
            StringBuilder params = new StringBuilder();
            params.append(NetString.MI_JSON_PARAMS);
            params.append(eventSubscribeReq.getEventName()).append(NetString.COMMA);
            params.append(eventSubscribeReq.getExternalApplicationSocket()).append(NetString.COMMA);
            params.append(eventSubscribeReq.getExpireTime());
            // 发起请求
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(urlBase + MiJsonUriEnum.EVENT_SUBSCRIBE.getUri() + params.toString())
                    .build();
            // 响应数据
            Response response = client.newCall(request).execute();
            eventSubscribeResp = JSONObject.parseObject(response.body().string(), MiEventSubscribeResp.class);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Request Opensips MI service [Event Subscribe] ERROR:", e);
        }
        return eventSubscribeResp;
    }

}
