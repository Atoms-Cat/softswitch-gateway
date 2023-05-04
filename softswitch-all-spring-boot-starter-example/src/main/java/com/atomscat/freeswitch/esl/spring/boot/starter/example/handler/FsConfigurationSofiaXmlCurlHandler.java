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
        paramList.add(new Param(SettingParamEnum.DEBUG.key, "0"));
        paramList.add(new Param(SettingParamEnum.SIP_TRACE.key, "no"));
        paramList.add(new Param(SettingParamEnum.SIP_CAPTURE.key, "no"));
        paramList.add(new Param(SettingParamEnum.WATCHDOG_ENABLED.key, "no"));
        paramList.add(new Param(SettingParamEnum.WATCHDOG_STEP_TIMEOUT.key, "30000"));
        paramList.add(new Param(SettingParamEnum.WATCHDOG_EVENT_TIMEOUT.key, "30000"));
        paramList.add(new Param(SettingParamEnum.LOG_AUTH_FAILURES.key, "false"));
        paramList.add(new Param(SettingParamEnum.FORWARD_UNSOLICITED_MWI_NOTIFY.key, "false"));
        paramList.add(new Param(SettingParamEnum.CONTEXT.key, "public"));
        paramList.add(new Param(SettingParamEnum.RFC2833_PT.key, "101"));
        paramList.add(new Param(SettingParamEnum.SIP_PORT.key, "$${internal_sip_port}"));
        paramList.add(new Param(SettingParamEnum.DIALPLAN.key, "XML"));
        paramList.add(new Param(SettingParamEnum.DTMF_DURATION.key, "2000"));
        paramList.add(new Param(SettingParamEnum.INBOUND_CODEC_PREFS.key, "$${global_codec_prefs}"));
        paramList.add(new Param(SettingParamEnum.OUTBOUND_CODEC_PREFS.key, "$${global_codec_prefs}"));
        paramList.add(new Param(SettingParamEnum.RTP_TIMER_NAME.key, "soft"));
        paramList.add(new Param(SettingParamEnum.RTP_IP.key, "$${local_ip_v4}"));
        paramList.add(new Param(SettingParamEnum.SIP_IP.key, "$${local_ip_v4}"));
        paramList.add(new Param(SettingParamEnum.HOLD_MUSIC.key, "$${hold_music}"));
        paramList.add(new Param(SettingParamEnum.APPLY_NAT_ACL.key, "nat.auto"));
        paramList.add(new Param(SettingParamEnum.APPLY_INBOUND_ACL.key, "domains"));
        paramList.add(new Param(SettingParamEnum.LOCAL_NETWORK_ACL.key, "localnet.auto"));
        paramList.add(new Param(SettingParamEnum.RECORD_PATH.key, "$${recordings_dir}"));
        paramList.add(new Param(SettingParamEnum.RECORD_TEMPLATE.key, "${caller_id_number}.${target_domain}.${strftime(%Y_%m_%d_%H_%M_%S)}.wav"));
        paramList.add(new Param(SettingParamEnum.MANAGE_PRESENCE.key, "true"));
        paramList.add(new Param(SettingParamEnum.PRESENCE_HOSTS.key, "$${domain},$${local_ip_v4}"));
        paramList.add(new Param(SettingParamEnum.PRESENCE_PRIVACY.key, "$${presence_privacy}"));
        paramList.add(new Param(SettingParamEnum.INBOUND_CODEC_NEGOTIATION.key, "generous"));
        paramList.add(new Param(SettingParamEnum.TLS.key, "$${internal_ssl_enable}"));
        paramList.add(new Param(SettingParamEnum.TLS_ONLY.key, "false"));
        paramList.add(new Param(SettingParamEnum.TLS_BIND_PARAMS.key, "transport=tls"));
        paramList.add(new Param(SettingParamEnum.TLS_SIP_PORT.key, "$${internal_tls_port}"));
        paramList.add(new Param(SettingParamEnum.TLS_PASSPHRASE.key, ""));
        paramList.add(new Param(SettingParamEnum.TLS_VERIFY_DATE.key, "true"));
        paramList.add(new Param(SettingParamEnum.TLS_VERIFY_POLICY.key, "none"));
        paramList.add(new Param(SettingParamEnum.TLS_VERIFY_DEPTH.key, "2"));
        paramList.add(new Param(SettingParamEnum.TLS_VERIFY_IN_SUBJECTS.key, ""));
        paramList.add(new Param(SettingParamEnum.TLS_VERSION.key, "$${sip_tls_version}"));
        paramList.add(new Param(SettingParamEnum.TLS_CIPHERS.key, "$${sip_tls_ciphers}"));
        paramList.add(new Param(SettingParamEnum.INBOUND_LATE_NEGOTIATION.key, "true"));
        paramList.add(new Param(SettingParamEnum.INBOUND_ZRTP_PASSTHRU.key, "true"));
        paramList.add(new Param(SettingParamEnum.NONCE_TTL.key, "60"));
        paramList.add(new Param(SettingParamEnum.AUTH_CALLS.key, "$${internal_auth_calls}"));
        paramList.add(new Param(SettingParamEnum.INBOUND_REG_FORCE_MATCHING_USERNAME.key, "true"));
        paramList.add(new Param(SettingParamEnum.AUTH_ALL_PACKETS.key, "false"));
        paramList.add(new Param(SettingParamEnum.RTP_TIMEOUT_SEC.key, "300"));
        paramList.add(new Param(SettingParamEnum.RTP_HOLD_TIMEOUT_SEC.key, "1800"));
        paramList.add(new Param(SettingParamEnum.FORCE_REGISTER_DOMAIN.key, "$${domain}"));
        paramList.add(new Param(SettingParamEnum.FORCE_SUBSCRIPTION_DOMAIN.key, "$${domain}"));
        paramList.add(new Param(SettingParamEnum.FORCE_REGISTER_DB_DOMAIN.key, "$${domain}"));
        paramList.add(new Param(SettingParamEnum.WS_BINDING.key, ":5066"));
        paramList.add(new Param(SettingParamEnum.WSS_BINDING.key, ":7443"));
        paramList.add(new Param(SettingParamEnum.CHALLENGE_REALM.key, "auto_from"));
        paramList.add(new Param(SettingParamEnum.APPLY_CANDIDATE_ACL.key, "wan"));
        paramList.add(new Param(SettingParamEnum.APPLY_CANDIDATE_ACL.key, "localnet.auto"));
        paramList.add(new Param(SettingParamEnum.APPLY_CANDIDATE_ACL.key, "rfc1918.auto"));
        return paramList;
    }

    private List<Domain> getExternalDomains() {
        List<Domain> domainList = new ArrayList<>();
        domainList.add(new Domain("all", "false", "true"));
        return domainList;
    }


    private List<Param> getExternalSettings() {
        List<Param> paramList = new ArrayList<>();
        paramList.add(new Param(SettingParamEnum.DEBUG.key, "0"));
        paramList.add(new Param(SettingParamEnum.SIP_TRACE.key, "no"));
        paramList.add(new Param(SettingParamEnum.SIP_CAPTURE.key, "no"));
        paramList.add(new Param(SettingParamEnum.RFC2833_PT.key, "101"));
        paramList.add(new Param(SettingParamEnum.SIP_PORT.key, "$${external_sip_port}"));
        paramList.add(new Param(SettingParamEnum.DIALPLAN.key, "XML"));
        paramList.add(new Param(SettingParamEnum.CONTEXT.key, "public"));
        paramList.add(new Param(SettingParamEnum.DTMF_DURATION.key, "2000"));
        paramList.add(new Param(SettingParamEnum.INBOUND_CODEC_PREFS.key, "$${global_codec_prefs}"));
        paramList.add(new Param(SettingParamEnum.OUTBOUND_CODEC_PREFS.key, "$${outbound_codec_prefs}"));
        paramList.add(new Param(SettingParamEnum.HOLD_MUSIC.key, "$${hold_music}"));
        paramList.add(new Param(SettingParamEnum.RTP_TIMER_NAME.key, "soft"));
        paramList.add(new Param(SettingParamEnum.LOCAL_NETWORK_ACL.key, "localnet.auto"));
        paramList.add(new Param(SettingParamEnum.MANAGE_PRESENCE.key, "false"));
        paramList.add(new Param(SettingParamEnum.INBOUND_CODEC_NEGOTIATION.key, "generous"));
        paramList.add(new Param(SettingParamEnum.NONCE_TTL.key, "60"));
        paramList.add(new Param(SettingParamEnum.AUTH_CALLS.key, "false"));
        paramList.add(new Param(SettingParamEnum.INBOUND_LATE_NEGOTIATION.key, "true"));
        paramList.add(new Param(SettingParamEnum.INBOUND_ZRTP_PASSTHRU.key, "true"));
        paramList.add(new Param(SettingParamEnum.RTP_IP.key, "$${local_ip_v4}"));
        paramList.add(new Param(SettingParamEnum.SIP_IP.key, "$${local_ip_v4}"));
        paramList.add(new Param(SettingParamEnum.RTP_TIMEOUT_SEC.key, "300"));
        paramList.add(new Param(SettingParamEnum.RTP_HOLD_TIMEOUT_SEC.key, "1800"));
        paramList.add(new Param(SettingParamEnum.TLS.key, "$${external_ssl_enable}"));
        paramList.add(new Param(SettingParamEnum.TLS_ONLY.key, "false"));
        paramList.add(new Param(SettingParamEnum.TLS_BIND_PARAMS.key, "transport=tls"));
        paramList.add(new Param(SettingParamEnum.TLS_SIP_PORT.key, "$${external_tls_port}"));
        paramList.add(new Param(SettingParamEnum.TLS_PASSPHRASE.key, ""));
        paramList.add(new Param(SettingParamEnum.TLS_VERIFY_DATE.key, "true"));
        paramList.add(new Param(SettingParamEnum.TLS_VERIFY_POLICY.key, "none"));
        paramList.add(new Param(SettingParamEnum.TLS_VERIFY_DEPTH.key, "2"));
        paramList.add(new Param(SettingParamEnum.TLS_VERIFY_IN_SUBJECTS.key, ""));
        paramList.add(new Param(SettingParamEnum.TLS_VERSION.key, "$${sip_tls_version}"));
        paramList.add(new Param(SettingParamEnum.APPLY_CANDIDATE_ACL.key, "localnet.auto"));
        paramList.add(new Param(SettingParamEnum.APPLY_CANDIDATE_ACL.key, "rfc1918.auto"));
//        paramList.add(new Param(SettingParamEnum.EXT_RTP_IP.key,"$${external_rtp_ip}"));
//        paramList.add(new Param(SettingParamEnum.EXT_SIP_IP.key,"$${external_sip_ip}"));
        return paramList;
    }

    private List<Gateway> getGatewayList() {
        List<Gateway> gatewayList = new ArrayList<>();
        gatewayList.add(new Gateway().setName("out_gateway").setParam(getParamList()));
        return gatewayList;
    }

    private List<Param> getParamList() {
        List<Param> paramList = new ArrayList<>();
        paramList.add(new Param(GatewayParamEnum.REALM.key, "192.168.15.131:5060"));
        paramList.add(new Param(GatewayParamEnum.FROM_DOMAIN.key, "192.168.15.130"));
        paramList.add(new Param(GatewayParamEnum.REGISTER.key, "false"));
        paramList.add(new Param(GatewayParamEnum.RETRY_SECONDS.key, "30"));
        return paramList;
    }
}