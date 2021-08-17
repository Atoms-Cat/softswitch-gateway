package link.thingscloud.freeswitch.xml.controller;

import link.thingscloud.freeswitch.xml.service.XmlCurlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/freeswitch")
public class XmlCurlController {
    @Autowired
    private XmlCurlService cdrService;

    private static final String ERROR_RESULT = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
            "<document type=\"freeswitch/xml\">\n" +
            "  <section name=\"result\">\n" +
            "    <result status=\"not found\"/>\n" +
            "  </section>\n" +
            "</document>";

    /**
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/xml/curl", produces = {MediaType.TEXT_XML_VALUE})
    public String curl(HttpServletRequest request) {
        String section = request.getParameter("section");
        String keyValue = request.getParameter("key_value");
        log.info("section : {} , keyValue : {}", section, keyValue);
        // todo
        cdrService.handle(keyValue);
        return ERROR_RESULT;
    }
}
