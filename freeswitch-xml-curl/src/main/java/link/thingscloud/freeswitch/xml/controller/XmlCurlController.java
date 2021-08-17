package link.thingscloud.freeswitch.xml.controller;

import com.alibaba.fastjson.JSONObject;
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
        log.info(JSONObject.toJSONString(request.getParameterMap()));;

        // todo dialplan|configuration|directory|phrases
        cdrService.handle(keyValue);
        //if ("dialplan".equals(section)) {
            return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<document type=\"freeswitch/xml\">\n" +
                    "  <section name=\"dialplan\" description=\"RE Dial Plan For FreeSwitch\">\n" +
                    "    <context name=\"default\">\n" +
                    "      <extension name=\"call\">\n" +
                    "        <condition field=\"destination_number\" expression=\"^([0-9]\\d+)$\">\n" +
                    "            <action application=\"answer\"/>\n" +
                    "            <action application=\"sleep\" data=\"2000\"/>\n" +
                    "        </condition>\n" +
                    "      </extension>\n" +
                    "    </context>\n" +
                    "    <context name=\"public\">\n" +
                    "      <extension name=\"call\">\n" +
                    "        <condition field=\"destination_number\" expression=\"^([0-9]\\d+)$\">\n" +
                    "            <action application=\"answer\"/>\n" +
                    "            <action application=\"sleep\" data=\"2000\"/>\n" +
                    "        </condition>\n" +
                    "      </extension>\n" +
                    "    </context>\n" +
                    "  </section>\n" +
                    "</document>";
        //}

       // return ERROR_RESULT;
    }
}
