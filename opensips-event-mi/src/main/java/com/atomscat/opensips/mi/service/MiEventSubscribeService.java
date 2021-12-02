package com.atomscat.opensips.mi.service;


import com.atomscat.opensips.mi.entitys.request.MiEventSubscribeReq;
import com.atomscat.opensips.mi.entitys.response.MiEventSubscribeResp;

/**
 *
 * @author : zhouhailin
 * @version 1.0.0
 */
public interface MiEventSubscribeService {
    /**
     *
     * @param urlBase
     * @param eventSubscribeReq
     * @return
     */
    MiEventSubscribeResp setEventSubscribe(String urlBase, MiEventSubscribeReq eventSubscribeReq);
}
