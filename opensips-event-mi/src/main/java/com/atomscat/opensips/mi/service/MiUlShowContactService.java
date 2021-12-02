package com.atomscat.opensips.mi.service;


import com.atomscat.opensips.mi.entitys.request.MiUlShowContactReq;
import com.atomscat.opensips.mi.entitys.response.MiUlShowContactResp;

/**
 *
 * @author : zhouhailin
 * @version 1.0.0
 */
public interface MiUlShowContactService {
    /**
     *
     * @param urlBase
     * @param ulShowContactReq
     * @return
     */
    MiUlShowContactResp getUlShowContact(String urlBase, MiUlShowContactReq ulShowContactReq);
}
