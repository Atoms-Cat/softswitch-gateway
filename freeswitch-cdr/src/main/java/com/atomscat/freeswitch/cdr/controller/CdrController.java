package com.atomscat.freeswitch.cdr.controller;

import com.atomscat.freeswitch.cdr.service.CdrService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>CdrController class.</p>
 *
 * @author : <a href="everyone@aliyun.com">everyone</a>
 * @version $Id: $Id
 */
@Slf4j
@RestController
@RequestMapping("/freeswitch")
@RequiredArgsConstructor
public class CdrController {

    private final CdrService cdrService;

    /**
     * <p>cdr.</p>
     *
     * @param httpHeaders a {@link org.springframework.http.HttpHeaders} object.
     * @param reqText     a {@link java.lang.String} object.
     */
    @RequestMapping("/cdr")
    public void cdr(@RequestHeader HttpHeaders httpHeaders, @RequestBody String reqText) {
        log.debug("cdr httpHeaders : [{}]", httpHeaders);
        log.debug("cdr reqText     : [{}]", reqText);
        cdrService.handle(reqText);
    }

}
