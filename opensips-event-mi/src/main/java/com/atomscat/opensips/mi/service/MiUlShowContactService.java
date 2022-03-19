package com.atomscat.opensips.mi.service;


import com.atomscat.opensips.mi.entitys.request.MiUlShowContactReq;
import com.atomscat.opensips.mi.entitys.response.MiUlShowContactResp;

/**
 * @author : zhouhailin
 * @version 1.0.0
 */
public interface MiUlShowContactService {
    /**
     * @param urlBase OpenSips MI Service address base url
     * @param ulShowContactReq a {@link MiUlShowContactReq} request param
     * @return a {@link MiUlShowContactResp} response param
     */
    MiUlShowContactResp getUlShowContact(String urlBase, MiUlShowContactReq ulShowContactReq);
}
