package link.thingscloud.freeswitch.xml.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/freeswitch")
public class CurlController {
    private static final String ERROR_RESULT = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
            "<document type=\"freeswitch/xml\">\n" +
            "  <section name=\"result\">\n" +
            "    <result status=\"not found\"/>\n" +
            "  </section>\n" +
            "</document>";
    /**
     * <p>cdr.</p>
     *
     * @param httpHeaders a {@link org.springframework.http.HttpHeaders} object.
     * @param reqText     a {@link java.lang.String} object.
     */
    @RequestMapping(value = "/xml/curl", produces = {MediaType.TEXT_XML_VALUE})
    public String curl(@RequestHeader HttpHeaders httpHeaders, @RequestBody String reqText, HttpServletRequest request) {
        log.info("cdr httpHeaders : [{}]", httpHeaders);
        log.info("cdr reqText     : [{}]", reqText);
        String section = request.getParameter("section");
        String keyValue = request.getParameter("key_value");
        log.info("section : {} , keyValue : {}", section, keyValue);
        return ERROR_RESULT;
    }
}
