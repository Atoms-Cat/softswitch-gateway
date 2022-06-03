package com.atomscat.freeswitch.xml.controller;

import com.alibaba.fastjson.JSONObject;
import com.atomscat.freeswitch.xml.service.XmlCurlService;
import com.atomscat.freeswitch.xml.utils.BasicAuthenticationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Slf4j
@RestController
@RequestMapping("/freeswitch")
public class XmlCurlController {
    @Autowired
    private XmlCurlService cdrService;

    @Value("${com.atomscat.freeswitch.xml-curl.auth-scheme:#{null}}")
    private String authScheme;

    @Value("${com.atomscat.freeswitch.xml-curl.credentials.username:#{null}}")
    private String username;

    @Value("${com.atomscat.freeswitch.xml-curl.credentials.password:#{null}}")
    private String password;

    @GetMapping(value = "/xml/curl", produces = {MediaType.TEXT_XML_VALUE})
    public String getCurl(HttpServletRequest request) {
        if (!authScheme(request)) {
            return null;
        }
        log.debug("{} , [{}]", request.getParameterMap().size(), JSONObject.toJSONString(request.getParameterMap()));
        // todo dialplan|configuration|directory|phrases
        String resp = cdrService.handle(request);
        log.debug("resp xml: {}", resp);
        return resp;
    }


    @PostMapping(value = "/xml/curl", produces = {MediaType.TEXT_XML_VALUE})
    public String postCurl(HttpServletRequest request) {
        if (!authScheme(request)) {
            return null;
        }
        log.debug("{} , [{}]", request.getParameterMap().size(), JSONObject.toJSONString(request.getParameterMap()));
        // todo dialplan|configuration|directory|phrases
        String resp = cdrService.handle(request);
        log.debug("resp xml: {}", resp);
        return resp;
    }

    private Boolean authScheme(HttpServletRequest request) {
        // auth-scheme: digest
        // @see com.atomscat.freeswitch.xml.config.SecurityConfiguration
        if (authScheme != null && "digest".equals(authScheme)) {
            return true;
        }
        // auth-scheme: basic, you need delete pom.xml security dependency
        if (authScheme != null && "basic".equals(authScheme) && request != null) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null) {
                return false;
            }
            String base64DecodeStr = authHeader.substring(6);
            String resp = BasicAuthenticationUtil.base64Decode(base64DecodeStr);
            log.info("Authorization : {} {}", authHeader, resp);
            if ((username + ":" + password).equals(resp)) {
                return true;
            }
        }
        return true;
    }

}
