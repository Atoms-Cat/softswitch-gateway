package com.atomscat.freeswitch.esl.spring.boot.starter.example.handler;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
@XmlCurlSectionName(SectionNames.DIRECTORY)
public class FsDirectoryXmlCurlHandler implements XmlCurlHandler {

    @Override
    public String handleXmlCurl(XmlCurl xmlCurl) throws JsonProcessingException {

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
        ))).setGroups(new Groups(Arrays.asList(new Group(new Users(Arrays.asList(new User("1090",null, new Params(Arrays.asList(new Param(ParamEnum.PASSWORD.name, "123456789"))), null, null)))))))
        ;

        return domain.toXmlString();
    }

}
