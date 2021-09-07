package com.atomscat.freeswitch.xml.controller;

import com.alibaba.fastjson.JSONObject;
import com.atomscat.freeswitch.xml.service.XmlCurlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/freeswitch")
public class XmlCurlController {
    @Autowired
    private XmlCurlService cdrService;

    /**
     * @param request
     * @return
     */
    @GetMapping(value = "/xml/curl", produces = {MediaType.TEXT_XML_VALUE})
    public String curl(HttpServletRequest request) {
        log.info("{} , [{}]", request.getParameterMap().size(), JSONObject.toJSONString(request.getParameterMap()));
        // todo dialplan|configuration|directory|phrases
        return cdrService.handle(request);
    }
}
