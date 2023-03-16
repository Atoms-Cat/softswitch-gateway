package com.atomscat.freeswitch.esl.spring.boot.starter.example.handler;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atomscat.freeswitch.esl.InboundClient;
import com.atomscat.freeswitch.esl.constant.EventNames;
import com.atomscat.freeswitch.esl.transport.SendEvent;
import com.atomscat.freeswitch.esl.transport.SendMsg;
import com.atomscat.freeswitch.xml.annotation.XmlCurlSectionName;
import com.atomscat.freeswitch.xml.constant.SectionNames;
import com.atomscat.freeswitch.xml.domain.XmlCurl;
import com.atomscat.freeswitch.xml.domain.directory.Domain;
import com.atomscat.freeswitch.xml.domain.directory.group.Group;
import com.atomscat.freeswitch.xml.domain.directory.group.Groups;
import com.atomscat.freeswitch.xml.domain.directory.param.Param;
import com.atomscat.freeswitch.xml.domain.directory.param.ParamEnum;
import com.atomscat.freeswitch.xml.domain.directory.param.Params;
import com.atomscat.freeswitch.xml.domain.directory.user.User;
import com.atomscat.freeswitch.xml.domain.directory.user.Users;
import com.atomscat.freeswitch.xml.domain.directory.variable.Variable;
import com.atomscat.freeswitch.xml.domain.directory.variable.VariableEnum;
import com.atomscat.freeswitch.xml.domain.directory.variable.Variables;
import com.atomscat.freeswitch.xml.handler.XmlCurlHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@XmlCurlSectionName(SectionNames.DIRECTORY)
public class FsDirectoryXmlCurlHandler implements XmlCurlHandler {

    private final InboundClient inboundClient;

    @Override
    public String handleXmlCurl(XmlCurl xmlCurl) throws JsonProcessingException {
        log.info("directory xml curl req json : [{}]", JSONObject.toJSONString(xmlCurl));

        return this.getDomain(xmlCurl);
    }

    private String getDomain(XmlCurl xmlCurl) throws JsonProcessingException {

        // todo
        if (!"1090".equals(xmlCurl.getMetadata().get("user"))) {
            throw new RuntimeException("can`t find user id");
        }

        Domain domain = new Domain();
        domain.setName("$${domain}")
        .setParams(new Params().setParam(Arrays.asList(
                new Param(ParamEnum.DIAL_STRING.name,"{^^:sip_invite_domain=${dialed_domain}:presence_id=${dialed_user}@${dialed_domain}}${sofia_contact(*/${dialed_user}@${dialed_domain})},${verto_contact(${dialed_user}@${dialed_domain})}"),
                new Param(ParamEnum.JSONRPC_ALLOWED_METHODS.name, "verto")
        ))).setVariables(new Variables(Arrays.asList(
                new Variable(VariableEnum.RECORD_STEREO.name, "true"),
                new Variable(VariableEnum.DEFAULT_GATEWAY.name, "$${default_provider}"),
                new Variable(VariableEnum.DEFAULT_AREACODE.name, "$${default_areacode}"),
                new Variable(VariableEnum.TRANSFER_FALLBACK_EXTENSION.name, "operator")
        ))).setGroups(new Groups(Arrays.asList(new Group(
                new Users(Arrays.asList(
                        new User("1090",null,
                                new Params(Arrays.asList(new Param(ParamEnum.PASSWORD.name, "123456789"))),
                                new Variables(Arrays.asList(new Variable(VariableEnum.USER_CONTEXT.name, "default"), new Variable(VariableEnum.EFFECTIVE_CALLER_ID_NUMBER.name, "1090"))),
                                null)))
                )
                )
                ))
        ;

        return domain.toXmlString();
    }

//    @Scheduled(cron = "0/5 * * * * ?")
//    private void chat() {
//        String passwd = "chat: " + RandomUtil.randomString(16);
//        log.info("send chat Scheduled : [{}]", passwd);
//
//        inboundClient.sendSyncApiCommand("127.0.0.1:8021", "chat", "sip|Server@FS.local|1090@192.168.10.120|"+passwd, commandResponse -> {
//            log.info("command send resp [{}]", JSON.toJSONString(commandResponse));
//        });
//    }

    @Scheduled(cron = "0/5 * * * * ?")
    private void sendEvent() {
        String passwd = "event: " + RandomUtil.randomString(16);
        log.info("sendEvent Scheduled : [{}]", passwd);
        SendEvent sendEvent = new SendEvent(EventNames.SEND_MESSAGE);
        sendEvent
                .addLine("profile","internal")
                .addLine("content-type", "text/plain")
                .addLine("user", "1090")
                .addLine("host","192.168.10.120")
                .addLine("content-length", String.valueOf(passwd.length()))
                .addLine("uuid", UUID.randomUUID().toString())
                ;
        sendEvent.addBody(passwd);
        inboundClient.sendEvent("127.0.0.1:8021", sendEvent, commandResponse -> {
           log.info("sendEvent resp [{}]", JSON.toJSONString(commandResponse));
        });
    }


}
