package com.atomscat.freeswitch.esl.spring.boot.starter.example.handler;

import com.atomscat.freeswitch.xml.annotation.XmlCurlSectionName;
import com.atomscat.freeswitch.xml.constant.SectionNames;
import com.atomscat.freeswitch.xml.domain.XmlCurl;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.GlobalSettings;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.Param;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.Sofia;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.domain.Domain;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.domain.Domains;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.gateway.Gateway;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.gateway.GatewayParamEnum;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.gateway.Gateways;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.profile.Profile;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.profile.Profiles;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.setting.SettingParamEnum;
import com.atomscat.freeswitch.xml.domain.configuration.sofia.setting.Settings;
import com.atomscat.freeswitch.xml.handler.XmlCurlHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * fs 執行：reload mod_sofia 重新加載sofia.conf配置
 * 动态配置 external 的 gateway配置
 * freeswitch 启动或者 执行 reload mod_sofia 指令才会获取
 */
@Slf4j
@Service
@XmlCurlSectionName(value = SectionNames.CONFIGURATION, key = SectionNames.Configuration.SOFIA)
public class FsConfigurationSofiaXmlCurlHandler implements XmlCurlHandler {
    @Override
    public String handleXmlCurl(XmlCurl xmlCurl) {
        log.info("FsConfigurationSofiaXmlCurlHandler: [{}]", xmlCurl);
        String xml = null;
        try {
            xml = getConfiguration();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info("FsConfigurationSofiaXmlCurlHandler: [{}]", xml);
        return xml;
//
//        return "<configuration name=\"sofia.conf\" description=\"sofia endpoint\">\n" +
//                "  <profiles>\n" +
//                "    <profile name=\"example\">\n" +
//                "      <gateways>\n" +
//                "         <gateway name=\"asterisk_1\">\n" +
//                "               <param name=\"realm\" value=\"1.2.3.4:5060\"/>\n" +
//                "               <param name=\"username\" value=\"foo\"/>\n" +
//                "               <param name=\"password\" value=\"1234\"/>\n" +
//                "               <param name=\"register\" value=\"false\"/>\n" +
//                "               <param name=\"retry-seconds\" value=\"30\"/>\n" +
//                "           </gateway>\n" +
//                "      </gateways>\n" +
//                "      <settings> \n" +
//                "      </settings>\n" +
//                "    </profile>\n" +
//                "  </profiles>\n" +
//                "</configuration>";
    }

    private String getConfiguration() throws JsonProcessingException {
        Sofia configuration = new Sofia();
        configuration.setDescription("sofia endpoint");
        configuration.setProfiles(getProfiles());
        configuration.setGlobalSettings(new GlobalSettings().setParam(getGlobalSettingsParamList()));
        return configuration.toXmlString();
    }

    private List<Param> getGlobalSettingsParamList() {
        List<Param> paramList = new ArrayList<>();
        paramList.add(new Param("log_level", "0"));
        paramList.add(new Param("debug_presence", "0"));
        return paramList;
    }


    private Profiles getProfiles() {
        return new Profiles().setProfile(getProfileList());
    }

    private List<Profile> getProfileList() {
        List<Profile> profiles = new ArrayList<>();
        profiles.add(new Profile()
                .setName("internal")
                .setDomains(new Domains().setDomain(getInternalDomains()))
                .setSettings(new Settings().setParam(getInternalSettings())));
        profiles.add(new Profile()
                .setName("external")
                .setDomains(new Domains().setDomain(getExternalDomains()))
                .setSettings(new Settings().setParam(getExternalSettings()))
                .setGateways(new Gateways().setGateway(getGatewayList())));
        return profiles;
    }

    private List<Domain> getInternalDomains() {
        List<Domain> domainList = new ArrayList<>();
        domainList.add(new Domain("all", "true", "false"));
        return domainList;
    }

    private List<Param> getInternalSettings() {
        List<Param> paramList = new ArrayList<>();
        paramList.add(new Param(SettingParamEnum.DEBUG.name, "0"));
        paramList.add(new Param(SettingParamEnum.SIP_TRACE.name, "no"));
        paramList.add(new Param(SettingParamEnum.SIP_CAPTURE.name, "no"));
        paramList.add(new Param(SettingParamEnum.WATCHDOG_ENABLED.name, "no"));
        paramList.add(new Param(SettingParamEnum.WATCHDOG_STEP_TIMEOUT.name, "30000"));
        paramList.add(new Param(SettingParamEnum.WATCHDOG_EVENT_TIMEOUT.name, "30000"));
        paramList.add(new Param(SettingParamEnum.LOG_AUTH_FAILURES.name, "false"));
        paramList.add(new Param(SettingParamEnum.FORWARD_UNSOLICITED_MWI_NOTIFY.name, "false"));
        paramList.add(new Param(SettingParamEnum.CONTEXT.name, "public"));
        paramList.add(new Param(SettingParamEnum.RFC2833_PT.name, "101"));
        paramList.add(new Param(SettingParamEnum.SIP_PORT.name, "$${internal_sip_port}"));
        paramList.add(new Param(SettingParamEnum.DIALPLAN.name, "XML"));
        paramList.add(new Param(SettingParamEnum.DTMF_DURATION.name, "2000"));
        paramList.add(new Param(SettingParamEnum.INBOUND_CODEC_PREFS.name, "$${global_codec_prefs}"));
        paramList.add(new Param(SettingParamEnum.OUTBOUND_CODEC_PREFS.name, "$${global_codec_prefs}"));
        paramList.add(new Param(SettingParamEnum.RTP_TIMER_NAME.name, "soft"));
        paramList.add(new Param(SettingParamEnum.RTP_IP.name, "$${local_ip_v4}"));
        paramList.add(new Param(SettingParamEnum.SIP_IP.name, "$${local_ip_v4}"));
        paramList.add(new Param(SettingParamEnum.HOLD_MUSIC.name, "$${hold_music}"));
        paramList.add(new Param(SettingParamEnum.APPLY_NAT_ACL.name, "nat.auto"));
        paramList.add(new Param(SettingParamEnum.APPLY_INBOUND_ACL.name, "domains"));
        paramList.add(new Param(SettingParamEnum.LOCAL_NETWORK_ACL.name, "localnet.auto"));
        paramList.add(new Param(SettingParamEnum.RECORD_PATH.name, "$${recordings_dir}"));
        paramList.add(new Param(SettingParamEnum.RECORD_TEMPLATE.name, "${caller_id_number}.${target_domain}.${strftime(%Y_%m_%d_%H_%M_%S)}.wav"));
        paramList.add(new Param(SettingParamEnum.MANAGE_PRESENCE.name, "true"));
        paramList.add(new Param(SettingParamEnum.PRESENCE_HOSTS.name, "$${domain},$${local_ip_v4}"));
        paramList.add(new Param(SettingParamEnum.PRESENCE_PRIVACY.name, "$${presence_privacy}"));
        paramList.add(new Param(SettingParamEnum.INBOUND_CODEC_NEGOTIATION.name, "generous"));
        paramList.add(new Param(SettingParamEnum.TLS.name, "$${internal_ssl_enable}"));
        paramList.add(new Param(SettingParamEnum.TLS_ONLY.name, "false"));
        paramList.add(new Param(SettingParamEnum.TLS_BIND_PARAMS.name, "transport=tls"));
        paramList.add(new Param(SettingParamEnum.TLS_SIP_PORT.name, "$${internal_tls_port}"));
        paramList.add(new Param(SettingParamEnum.TLS_PASSPHRASE.name, ""));
        paramList.add(new Param(SettingParamEnum.TLS_VERIFY_DATE.name, "true"));
        paramList.add(new Param(SettingParamEnum.TLS_VERIFY_POLICY.name, "none"));
        paramList.add(new Param(SettingParamEnum.TLS_VERIFY_DEPTH.name, "2"));
        paramList.add(new Param(SettingParamEnum.TLS_VERIFY_IN_SUBJECTS.name, ""));
        paramList.add(new Param(SettingParamEnum.TLS_VERSION.name, "$${sip_tls_version}"));
        paramList.add(new Param(SettingParamEnum.TLS_CIPHERS.name, "$${sip_tls_ciphers}"));
        paramList.add(new Param(SettingParamEnum.INBOUND_LATE_NEGOTIATION.name, "true"));
        paramList.add(new Param(SettingParamEnum.INBOUND_ZRTP_PASSTHRU.name, "true"));
        paramList.add(new Param(SettingParamEnum.NONCE_TTL.name, "60"));
        paramList.add(new Param(SettingParamEnum.AUTH_CALLS.name, "$${internal_auth_calls}"));
        paramList.add(new Param(SettingParamEnum.INBOUND_REG_FORCE_MATCHING_USERNAME.name, "true"));
        paramList.add(new Param(SettingParamEnum.AUTH_ALL_PACKETS.name, "false"));
        paramList.add(new Param(SettingParamEnum.RTP_TIMEOUT_SEC.name, "300"));
        paramList.add(new Param(SettingParamEnum.RTP_HOLD_TIMEOUT_SEC.name, "1800"));
        paramList.add(new Param(SettingParamEnum.FORCE_REGISTER_DOMAIN.name, "$${domain}"));
        paramList.add(new Param(SettingParamEnum.FORCE_SUBSCRIPTION_DOMAIN.name, "$${domain}"));
        paramList.add(new Param(SettingParamEnum.FORCE_REGISTER_DB_DOMAIN.name, "$${domain}"));
        paramList.add(new Param(SettingParamEnum.WS_BINDING.name, ":5066"));
        paramList.add(new Param(SettingParamEnum.WSS_BINDING.name, ":7443"));
        paramList.add(new Param(SettingParamEnum.CHALLENGE_REALM.name, "auto_from"));
        paramList.add(new Param(SettingParamEnum.APPLY_CANDIDATE_ACL.name, "wan"));
        paramList.add(new Param(SettingParamEnum.APPLY_CANDIDATE_ACL.name, "localnet.auto"));
        paramList.add(new Param(SettingParamEnum.APPLY_CANDIDATE_ACL.name, "rfc1918.auto"));
        return paramList;
    }

    private List<Domain> getExternalDomains() {
        List<Domain> domainList = new ArrayList<>();
        domainList.add(new Domain("all", "false", "true"));
        return domainList;
    }


    private List<Param> getExternalSettings() {
        List<Param> paramList = new ArrayList<>();
        paramList.add(new Param(SettingParamEnum.DEBUG.name, "0"));
        paramList.add(new Param(SettingParamEnum.SIP_TRACE.name, "no"));
        paramList.add(new Param(SettingParamEnum.SIP_CAPTURE.name, "no"));
        paramList.add(new Param(SettingParamEnum.RFC2833_PT.name, "101"));
        paramList.add(new Param(SettingParamEnum.SIP_PORT.name, "$${external_sip_port}"));
        paramList.add(new Param(SettingParamEnum.DIALPLAN.name, "XML"));
        paramList.add(new Param(SettingParamEnum.CONTEXT.name, "public"));
        paramList.add(new Param(SettingParamEnum.DTMF_DURATION.name, "2000"));
        paramList.add(new Param(SettingParamEnum.INBOUND_CODEC_PREFS.name, "$${global_codec_prefs}"));
        paramList.add(new Param(SettingParamEnum.OUTBOUND_CODEC_PREFS.name, "$${outbound_codec_prefs}"));
        paramList.add(new Param(SettingParamEnum.HOLD_MUSIC.name, "$${hold_music}"));
        paramList.add(new Param(SettingParamEnum.RTP_TIMER_NAME.name, "soft"));
        paramList.add(new Param(SettingParamEnum.LOCAL_NETWORK_ACL.name, "localnet.auto"));
        paramList.add(new Param(SettingParamEnum.MANAGE_PRESENCE.name, "false"));
        paramList.add(new Param(SettingParamEnum.INBOUND_CODEC_NEGOTIATION.name, "generous"));
        paramList.add(new Param(SettingParamEnum.NONCE_TTL.name, "60"));
        paramList.add(new Param(SettingParamEnum.AUTH_CALLS.name, "false"));
        paramList.add(new Param(SettingParamEnum.INBOUND_LATE_NEGOTIATION.name, "true"));
        paramList.add(new Param(SettingParamEnum.INBOUND_ZRTP_PASSTHRU.name, "true"));
        paramList.add(new Param(SettingParamEnum.RTP_IP.name, "$${local_ip_v4}"));
        paramList.add(new Param(SettingParamEnum.SIP_IP.name, "$${local_ip_v4}"));
        paramList.add(new Param(SettingParamEnum.RTP_TIMEOUT_SEC.name, "300"));
        paramList.add(new Param(SettingParamEnum.RTP_HOLD_TIMEOUT_SEC.name, "1800"));
        paramList.add(new Param(SettingParamEnum.TLS.name, "$${external_ssl_enable}"));
        paramList.add(new Param(SettingParamEnum.TLS_ONLY.name, "false"));
        paramList.add(new Param(SettingParamEnum.TLS_BIND_PARAMS.name, "transport=tls"));
        paramList.add(new Param(SettingParamEnum.TLS_SIP_PORT.name, "$${external_tls_port}"));
        paramList.add(new Param(SettingParamEnum.TLS_PASSPHRASE.name, ""));
        paramList.add(new Param(SettingParamEnum.TLS_VERIFY_DATE.name, "true"));
        paramList.add(new Param(SettingParamEnum.TLS_VERIFY_POLICY.name, "none"));
        paramList.add(new Param(SettingParamEnum.TLS_VERIFY_DEPTH.name, "2"));
        paramList.add(new Param(SettingParamEnum.TLS_VERIFY_IN_SUBJECTS.name, ""));
        paramList.add(new Param(SettingParamEnum.TLS_VERSION.name, "$${sip_tls_version}"));
        paramList.add(new Param(SettingParamEnum.APPLY_CANDIDATE_ACL.name, "localnet.auto"));
        paramList.add(new Param(SettingParamEnum.APPLY_CANDIDATE_ACL.name, "rfc1918.auto"));
//        paramList.add(new Param(SettingParamEnum.EXT_RTP_IP.name,"$${external_rtp_ip}"));
//        paramList.add(new Param(SettingParamEnum.EXT_SIP_IP.name,"$${external_sip_ip}"));
        return paramList;
    }

    private List<Gateway> getGatewayList() {
        List<Gateway> gatewayList = new ArrayList<>();
        gatewayList.add(new Gateway().setName("out_gateway").setParam(getParamList()));
        return gatewayList;
    }

    private List<Param> getParamList() {
        List<Param> paramList = new ArrayList<>();
        paramList.add(new Param(GatewayParamEnum.REALM.name, "192.168.15.131:5060"));
        paramList.add(new Param(GatewayParamEnum.FROM_DOMAIN.name, "192.168.15.130"));
        paramList.add(new Param(GatewayParamEnum.REGISTER.name, "false"));
        paramList.add(new Param(GatewayParamEnum.RETRY_SECONDS.name, "30"));
        return paramList;
    }
}