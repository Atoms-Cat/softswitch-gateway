package com.atomscat.freeswitch.esl.spring.boot.starter.example.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.shaded.com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DirectoryController {
    private static final String ERROR_RESULT = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
            "<document type=\"freeswitch/xml\">\n" +
            "  <section name=\"result\">\n" +
            "    <result status=\"not found\"/>\n" +
            "  </section>\n" +
            "</document>";

    @GetMapping(value = "/fs/directory/api", produces = {MediaType.TEXT_XML_VALUE})
    public String fsApi(HttpServletRequest request) {
        String section = request.getParameter("section");
        String keyValue = request.getParameter("key_value");
        log.info("section : {} , keyValue : {}", section, keyValue);

        String user = request.getParameter("user");
        String domain = request.getParameter("domain");

        log.info("req all param: {}", JSON.toJSONString(request.getParameterMap()));

        if ("1090".equals(user)) {

            return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<document type=\"freeswitch/xml\">\n" +
                    "  <section name=\"" + section + "\">\n" +
                    "<domain name=\"$${domain}\">" +
                    "<params>\n" +
                    "      <param name=\"dial-string\" value=\"{^^:sip_invite_domain=${dialed_domain}:presence_id=${dialed_user}@${dialed_domain}}${sofia_contact(*/${dialed_user}@${dialed_domain})},${verto_contact(${dialed_user}@${dialed_domain})}\"/>\n" +
                    "      <!-- These are required for Verto to function properly -->\n" +
                    "      <param name=\"jsonrpc-allowed-methods\" value=\"verto\"/>\n" +
                    "      <!-- <param name=\"jsonrpc-allowed-event-channels\" value=\"demo,conference,presence\"/> -->\n" +
                    "    </params>\n" +
                    "    <variables>\n" +
                    "      <variable name=\"record_stereo\" value=\"true\"/>\n" +
                    "      <variable name=\"default_gateway\" value=\"$${default_provider}\"/>\n" +
                    "      <variable name=\"default_areacode\" value=\"$${default_areacode}\"/>\n" +
                    "      <variable name=\"transfer_fallback_extension\" value=\"operator\"/>\n" +
                    "    </variables>" +
                    "    <groups>\n" +
                    "      <group name=\"default\">\n" +
                    "       <users>\n" +
                    "             <user id=\"1090\">\n" +
                    "    <params>\n" +
                    "      <param name=\"password\" value=\"123456789\"/>\n" +
                    "      <param name=\"vm-password\" value=\"1090\"/>\n" +
                    "    </params>\n" +
                    "    <variables>\n" +
                    "      <variable name=\"toll_allow\" value=\"domestic,international,local\"/>\n" +
                    "      <variable name=\"accountcode\" value=\"1000\"/>\n" +
                    "      <variable name=\"user_context\" value=\"default\"/>\n" +
                    "      <variable name=\"effective_caller_id_name\" value=\"Extension 1090\"/>\n" +
                    "      <variable name=\"effective_caller_id_number\" value=\"1000\"/>\n" +
                    "      <variable name=\"outbound_caller_id_name\" value=\"$${outbound_caller_name}\"/>\n" +
                    "      <variable name=\"outbound_caller_id_number\" value=\"$${outbound_caller_id}\"/>\n" +
                    "      <variable name=\"callgroup\" value=\"techsupport\"/>\n" +
                    "    </variables>\n" +
                    "  </user>" +
                    "       </users>\n" +
                    "      </group>" +
                    "    </groups>" +
                    "</domain>" +
                    "  </section>\n" +
                    "</document>";
        }
        return ERROR_RESULT;
    }
}
