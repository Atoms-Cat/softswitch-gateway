package com.atomscat.opensips.mi.service;


import com.atomscat.opensips.mi.entitys.request.MiEventSubscribeReq;
import com.atomscat.opensips.mi.entitys.response.MiEventSubscribeResp;

/**
 * @author : everyone
 * @version 1.0.0
 */
public interface MiEventSubscribeService {
    /**
     * @param urlBase  OpenSips MI Service address base url
     * @param eventSubscribeReq a {@link MiEventSubscribeReq} request param
     * @return a {@link MiEventSubscribeResp} response param
     */
    MiEventSubscribeResp setEventSubscribe(String urlBase, MiEventSubscribeReq eventSubscribeReq);
}
