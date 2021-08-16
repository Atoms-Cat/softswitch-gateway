package link.thingscloud.freeswitch.esl.spring.boot.starter.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author th158
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class FreeSwitchController {
    private static final String ERROR_RESULT = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
            "<document type=\"freeswitch/xml\">\n" +
            "  <section name=\"result\">\n" +
            "    <result status=\"not found\"/>\n" +
            "  </section>\n" +
            "</document>";

    @GetMapping(value = "/fs/api", produces = {MediaType.TEXT_XML_VALUE})
    public String fsApi(HttpServletRequest request) {
        String section = request.getParameter("section");
        String keyValue = request.getParameter("key_value");
        log.info("section : {} , keyValue : {}", section, keyValue);
        return ERROR_RESULT;
    }
}
