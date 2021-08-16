## oepnsips load balancing freeswitch，and pass rtp for rtpengine

![流程图](img/opensips-fs-rtpengine.png)

### 1. configuration opensips.cfg

```

#
# OpenSIPS residential configuration script
#     by OpenSIPS Solutions <team@opensips-solutions.com>
#
# This script was generated via "make menuconfig", from
#   the "Residential" scenario.
# You can enable / disable more features / functionalities by
#   re-generating the scenario with different options.#
#
# Please refer to the Core CookBook at:
#      https://opensips.org/Resources/DocsCookbooks
# for a explanation of possible statements, functions and parameters.
#




####### Global Parameters #########


log_level=3
log_stderror=no
log_facility=LOG_LOCAL0


children=4


/* uncomment the following lines to enable debugging */
#debug_mode=yes


/* uncomment the next line to enable the auto temporary blacklisting of
   not available destinations (default disabled) */
#disable_dns_blacklist=no


/* uncomment the next line to enable IPv6 lookup after IPv4 dns
   lookup failures (default disabled) */
#dns_try_ipv6=yes


/* comment the next line to enable the auto discovery of local aliases
   based on reverse DNS on IPs */
auto_aliases=no




listen=udp:192.168.10.109:5060   # CUSTOMIZE ME
listen=tcp:192.168.10.109:5060   # CUSTOMIZE ME
listen=ws:192.168.10.109:5062    # CUSTOMIZE ME
listen=wss:192.168.10.109:5063  # CUSTOMIZE ME


####### Modules Section ########


#set module path
mpath="/tools/install/opensips/lib64/opensips/modules/"


#### SIGNALING module
loadmodule "signaling.so"


#### StateLess module
loadmodule "sl.so"


#### Transaction Module
loadmodule "tm.so"
modparam("tm", "fr_timeout", 5)
modparam("tm", "fr_inv_timeout", 30)
modparam("tm", "restart_fr_on_each_reply", 0)
modparam("tm", "onreply_avp_mode", 1)


#### Record Route Module
loadmodule "rr.so"
/* do not append from tag to the RR (no need for this script) */
modparam("rr", "append_fromtag", 0)


#### MAX ForWarD module
loadmodule "maxfwd.so"


#### SIP MSG OPerationS module
loadmodule "sipmsgops.so"


#### FIFO Management Interface
loadmodule "mi_fifo.so"
modparam("mi_fifo", "fifo_name", "/tmp/opensips_fifo")
modparam("mi_fifo", "fifo_mode", 0666)


#### URI module
loadmodule "uri.so"
modparam("uri", "use_uri_table", 0)


#### PostgreSQL module
loadmodule "db_postgres.so"


#### HTTPD module
loadmodule "httpd.so"
modparam("httpd", "port", 8888)


#### USeR LOCation module
loadmodule "usrloc.so"
modparam("usrloc", "nat_bflag", "NAT")
modparam("usrloc", "db_mode",   2)
modparam("usrloc", "db_url","postgres://opensips:123456@192.168.10.114/opensips") # CUSTOMIZE ME




#### REGISTRAR module
loadmodule "registrar.so"
modparam("registrar", "tcp_persistent_flag", "TCP_PERSISTENT")
modparam("registrar", "received_avp", "$avp(received_nh)")/* uncomment the next line not to allow more than 10 contacts per AOR */
modparam("registrar", "max_contacts", 1)


#### ACCounting module
loadmodule "acc.so"
/* what special events should be accounted ? */
modparam("acc", "early_media", 0)
modparam("acc", "report_cancels", 0)
/* by default we do not adjust the direct of the sequential requests.
   if you enable this parameter, be sure the enable "append_fromtag"
   in "rr" module */
modparam("acc", "detect_direction", 0)
modparam("acc", "db_url","postgres://opensips:123456@192.168.10.114/opensips") # CUSTOMIZE ME


#### AUTHentication modules
loadmodule "auth.so"
loadmodule "auth_db.so"
modparam("auth_db", "calculate_ha1", yes)
modparam("auth_db", "password_column", "password")
modparam("auth_db|uri", "db_url","postgres://opensips:123456@192.168.10.114/opensips") # CUSTOMIZE ME
modparam("auth_db", "load_credentials", "")


#### ALIAS module
loadmodule "alias_db.so"
modparam("alias_db", "db_url","postgres://opensips:123456@192.168.10.114/opensips") # CUSTOMIZE ME


#### DOMAIN module
loadmodule "domain.so"
modparam("domain", "db_url",
        "postgres://opensips:123456@192.168.10.114/opensips") # CUSTOMIZE ME
modparam("domain", "db_mode", 1)   # Use caching
modparam("auth_db|usrloc|uri", "use_domain", 1)


#### PRESENCE modules
loadmodule "xcap.so"
loadmodule "presence.so"
loadmodule "presence_xml.so"
modparam("xcap|presence", "db_url","postgres://opensips:123456@192.168.10.114/opensips") # CUSTOMIZE ME
modparam("presence_xml", "force_active", 1)
modparam("presence", "fallback2db", 0)


#### DIALOG module
loadmodule "dialog.so"
modparam("dialog", "dlg_match_mode", 1)
modparam("dialog", "default_timeout", 21600)  # 6 hours timeout
modparam("dialog", "db_mode", 2)
modparam("dialog", "db_url","postgres://opensips:123456@192.168.10.114/opensips") # CUSTOMIZE ME


####  NAT modules
loadmodule "nathelper.so"
modparam("nathelper", "natping_interval", 10)
modparam("nathelper", "ping_nated_only", 1)
modparam("nathelper", "sipping_bflag", "SIP_PING_FLAG")
modparam("nathelper", "sipping_from", "sip:pinger@192.168.10.109") # CUSTOMIZE ME
modparam("nathelper", "received_avp", "$avp(received_nh)")


#loadmodule "rtpproxy.so"
#modparam("rtpproxy", "rtpproxy_sock", "udp:192.168.10.113:12221") # CUSTOMIZE ME
#modparam("rtpproxy", "db_url","postgres://opensips:123456@192.168.10.114/opensips") # CUSTOMIZE ME
#modparam("rtpproxy", "default_set", 1)
#modparam("rtpproxy", "rtpproxy_retr", 1)
#modparam("rtpproxy", "rtpproxy_autobridge", 1)


#### RTPengine protocol
loadmodule "rtpengine.so"
modparam("rtpengine", "db_url", "postgres://opensips:123456@192.168.10.114/opensips")
modparam("rtpengine", "socket_column", "socket_uri")




####  DIALPLAN module
loadmodule "dialplan.so"
modparam("dialplan", "db_url","postgres://opensips:123456@192.168.10.114/opensips") # CUSTOMIZE ME


####  DYNAMMIC ROUTING module
loadmodule "drouting.so"
modparam("drouting", "db_url","postgres://opensips:123456@192.168.10.114/opensips") # CUSTOMIZE ME


####  MI_HTTP module
loadmodule "mi_http.so"


loadmodule "mi_json.so"
modparam("mi_json", "mi_json_root", "json")


loadmodule "proto_udp.so"
loadmodule "proto_ws.so"
loadmodule "proto_wss.so"


modparam("proto_ws", "ws_port", 5062)
modparam("proto_wss", "wss_port", 5063)


loadmodule "proto_udp.so"
loadmodule "proto_tcp.so"
loadmodule "proto_tls.so"
loadmodule "tls_mgm.so"

modparam("tls_mgm","tls_method", "TLSv1_2")
modparam("tls_mgm","verify_cert", "0")
modparam("tls_mgm","require_cert", "0")
modparam("tls_mgm","certificate", "/tools/install/opensips/ssl/api.atomscat.com/cert.pem")
modparam("tls_mgm","private_key", "/tools/install/opensips/ssl/api.atomscat.com/privkey.pem")


loadmodule "cachedb_redis.so"
# modparam("cachedb_redis", "cachedb_url","redis:prod://Aa123456@192.168.10.114:6379/")
modparam("cachedb_redis", "cachedb_url","redis://root:Aa123456@192.168.10.114:6379/0")


#### DISPATCHER module
loadmodule "dispatcher.so"
modparam("dispatcher", "db_url", "postgres://opensips:123456@192.168.10.114/opensips")
modparam("dispatcher", "ds_ping_method", "INFO")
modparam("dispatcher", "ds_ping_interval", 5)
modparam("dispatcher", "ds_probing_threshhold", 2)
modparam("dispatcher", "ds_probing_mode", 1)


#### load_balancer
loadmodule "load_balancer.so"
modparam("load_balancer", "db_url", "postgres://opensips:123456@192.168.10.114/opensips")  # CUSTOMIZE ME
modparam("load_balancer", "probing_method", "INFO")
modparam("load_balancer", "probing_interval", 5)
modparam("load_balancer", "probing_reply_codes", "501, 403")


loadmodule "msilo.so"
modparam("msilo", "db_url", "postgres://opensips:123456@192.168.10.114/opensips")


#### AVPOPS module
loadmodule "avpops.so"
modparam("avpops", "db_url", "postgres://opensips:123456@192.168.10.114/opensips")


####### Routing Logic ########
# main request routing logic


route{

        # initial NAT handling; detect if the request comes from behind a NAT
        # and apply contact fixing
        force_rport();
        if (nat_uac_test("23")) {
                if (is_method("REGISTER")) {
                        fix_nated_register();
                        setbflag(NAT);
                } else {
                        fix_nated_contact();
                        setflag(NAT);
                }
        }

        if (!mf_process_maxfwd_header("10")) {
                send_reply("483","Too Many Hops");
                exit;
        }

        if (is_method("BYE")) {
                rtpengine_use_set("1");
                rtpengine_delete();
        }

        if ($pr == "ws" || $pr == "wss") {
                setflag(SRC_WS);
                xlog("ws: $pr " );
        }

        if (has_totag()) {

                # handle hop-by-hop ACK (no routing required)
                if ( is_method("ACK") && t_check_trans() ) {
                        t_relay();
                        exit;
                }

                # sequential request within a dialog should
                # take the path determined by record-routing
                if ( !loose_route() ) {
                        if (is_method("SUBSCRIBE") && is_myself("$rd")) {
                                # in-dialog subscribe requests
                                route(handle_presence);
                                exit;
                        }
                        # we do record-routing for all our traffic, so we should not
                        # receive any sequential requests without Route hdr.
                        send_reply("404","Not here");
                        exit;
                }


                # validate the sequential request against dialog
                if ( $DLG_status!=NULL && !validate_dialog() ) {
                        xlog("In-Dialog $rm from $si (callid=$ci) is not valid according to dialog\n");
                        ## exit;
                }

                if (is_method("BYE")) {
                        # do accounting even if the transaction fails
                        do_accounting("db","failed");


                }

                if (check_route_param("nat=yes"))
                        setflag(NAT);
                # route it out to whatever destination was set by loose_route()
                # in $du (destination URI).
                route(relay);
                exit;
        }


        # CANCEL processing
        if (is_method("CANCEL")) {
                if (t_check_trans())
                        t_relay();
                exit;
        }


        # absorb retransmissions, but do not create transaction
        t_check_trans();


        if ( !(is_method("REGISTER")  || is_from_gw() ) ) {

                if (is_from_local()) {
                        # authenticate if from local subscriber
                        # authenticate all initial non-REGISTER request that pretend to be
                        # generated by local subscriber (domain from FROM URI is local)
                        if (!proxy_authorize("", "subscriber")) {
                                proxy_challenge("", "0");
                                exit;
                        }
                        if (!db_check_from()) {
                                send_reply("403","Forbidden auth ID");
                                exit;
                        }

                        consume_credentials();
                        # caller authenticated

                } else {
                        # if caller is not local, then called number must be local
                        if (!is_uri_host_local()) {
                                send_reply("403","Relay Forbidden");
                                exit;
                        }
                }
        }

        # preloaded route checking
        if (loose_route()) {
                xlog("L_ERR",
                        "Attempt to route with preloaded Route's [$fu/$tu/$ru/$ci]");
                if (!is_method("ACK"))
                        send_reply("403","Preload Route denied");
                exit;
        }

        # record routing
        if (!is_method("REGISTER"))
                record_route();

        if (is_method("MESSAGE")) {
                # 获取呼叫的目标
                if (!lookup("location","m")) {
                }               
                xlog("MESSAGE: $ru");
        }

        # account only INVITEs
        if (is_method("INVITE")) {
                #if (!lookup("location","m")) {
                #}
                
                xlog("si: $si, sp: $sp, ru: $ru");
                # remove_hf("To");
                # append_hf("To: $ru\r\n", "From");

                # create dialog with timeout
                if ( !create_dialog("B") ) {
                        send_reply("500","Internal Server Error");
                        exit;
                }
                # 判断请求的来源ip、端口是不是“3”集群中的地址
                if (lb_is_destination("$si","$sp","3") ) {
                    xlog("FS TO OPENSIPS，再到终端");
                    if (!lookup("location", "m")) {
             
                    }
                } else {
                    xlog(“终端 TO OPENSIPS，再到 FS”);
                    # 选举 fs, todo  
                    if (!lb_start_or_next("1","pstn","ns")) {
                        send_reply("500","Internal Server Error");
                        exit;
                    }
                }
                $avp(received_nh) = $ru;

                xlog("$du , $ru ");

                do_accounting("db");
        }


        if (!is_uri_host_local()) {
                append_hf("P-hint: outbound\r\n");
                route(relay);
        }


        # requests for my domain


        if( is_method("PUBLISH|SUBSCRIBE"))
                        route(handle_presence);


        if (is_method("REGISTER")) {
                # authenticate the REGISTER requests
                if (!www_authorize("", "subscriber")) {
                        www_challenge("", "0");
                        exit;
                }
                if (!db_check_to()) {
                        send_reply("403","Forbidden auth ID");
                        exit;
                }
                if ($proto == "tcp" || $proto == "tls")
                        setflag(TCP_PERSISTENT);
                if (isflagset(NAT)) {
                        setbflag(SIP_PING_FLAG);
                }
                # store the registration and generate a SIP reply
                if($pr == "ws" || $pr == "wss") {
                    setbflag(DST_WS);
                    fix_nated_register();
                    if(!save("location","f")) {
                        xlog(" ws registerer error");
                    }
                } else {
                    if (!save("location")) {
                        xlog("failed to register AoR $tu\n");
                    }
                }
                xlog("$fd:$aU  $ct.fields(uri) ");
                cache_store("redis", "$fd:$au", "$ct.fields(uri)", 3600);
                exit;
        }


        if ($rU==NULL) {
                # request with no Username in RURI
                send_reply("484","Address Incomplete");
                exit;
        }

        # apply DB based aliases
        alias_db_lookup("dbaliases");

        # apply transformations from dialplan table
        dp_translate("0","$rU/$rU");

        if ($rU=~"^\+[1-9][0-9]+$") {
                if (!do_routing("0")) {
                        send_reply("500","No PSTN Route found");
                        exit;
                }
                route(relay);
                exit;
        }
        
        if (isbflagset(NAT)) setflag(NAT);
        
        # when routing via usrloc, log the missed calls also
        do_accounting("db","missed");
        route(relay);
}

route[relay] {
        # for INVITEs enable some additional helper routes
        if (is_method("INVITE")) {
                if (isflagset(NAT) && has_body("application/sdp") ) {
                    route(rtpengine_offer);
                }
                t_on_branch("per_branch_ops");
                t_on_reply("handle_nat");
                t_on_failure("missed_call");
               
                xlog(" $du");
        }

        if (isflagset(NAT)) {
                add_rr_param(";nat=yes");
        }
        if (!t_relay()) {
                send_reply("500","Internal Error");
        }
        exit;
}

# Presence route
route[handle_presence]
{
        if (!t_newtran()) {
                sl_reply_error();
                exit;
        }
        if(is_method("PUBLISH")) {
                handle_publish();
        } else
        if( is_method("SUBSCRIBE")) {
                handle_subscribe();
        }
        exit;
}

branch_route[per_branch_ops] {
        xlog("new branch at $ru\n");
}


onreply_route[handle_nat] {
        if (nat_uac_test("1")) {
                fix_nated_contact();
                xlog(" reply route: $du $ru $avp(received_nh)");
        }
        if ( isflagset(NAT) && has_body("application/sdp")) {
                route(rtpengine_answer);
        }
        xlog("incoming reply\n");
}

failure_route[missed_call] {
        if (t_was_cancelled()) {
                exit;
        }

        # redirect the failed to a different VM system
        if (t_check_status("486|408")) {
                # do not set the missed call flag again
                xlog("486|408");
                t_reply("486","Busy Here");
                exit;
        }
}


local_route {
        if (is_method("BYE") && $DLG_dir=="UPSTREAM") {
                acc_db_request("200 Dialog Timeout", "acc");
        }
}


route[rtpengine_offer] {
    if (isflagset(SRC_WS) && isbflagset(DST_WS))
        # - Web to web
        $var(reflags) = "trust-addres sreplace-origin replace-session-connection SDES-off ICE=force";
    else if (isflagset(SRC_WS))
        # - Web to SIP
        $var(reflags) = "trust-addres replace-origin replace-session-connection rtcp-mux-demux ICE=remove RTP/AVP";
    else if (isbflagset(DST_WS))
        # - SIP to web
        $var(reflags) = "trust-addres replace-origin replace-session-connection rtcp-mux-offer ICE=force transcode-opus transcode-G722 transcode-PCMU SDES-off UDP/TLS/RTP/SAVP";
    else
        # - SIP to SIP
        $var(reflags) = "trust-addres replace-origin replace-session-connection rtcp-mux-demux ICE=remove RTP/AVP";


    rtpengine_use_set("1");
    rtpengine_offer("$var(reflags)");
}


route[rtpengine_answer] {
    if (isflagset(SRC_WS) && isbflagset(DST_WS))
        # - Web to web
        $var(reflags) = "trust-addres replace-origin replace-session-connection SDES-off ICE=force";
    else if (isflagset(SRC_WS))
        # - Web to SIP
        $var(reflags) = "trust-addres replace-origin replace-session-connection rtcp-mux-require ICE=force RTP/SAVPF";
    else if (isbflagset(DST_WS))
        # - SIP to web
        $var(reflags) = "trust-addres replace-origin replace-session-connection rtcp-mux-offer ICE=force transcode-opus transcode-G722 transcode-PCMU SDES-off UDP/TLS/RTP/SAVP";
    else
        # - SIP to SIP
        $var(reflags) = "trust-addres replace-origin replace-session-connection rtcp-mux-demux ICE=remove RTP/AVP";
        
    rtpengine_use_set("1");
    rtpengine_answer("$var(reflags)");
}
```


### 2. opensips database, customize ip
#### dispatcher table
```
INSERT INTO "dispatcher" ("id", "setid", "destination", "socket", "state", "weight", "priority", "attrs", "description") VALUES (1, 1, 'sip:192.168.10.114:5060', '', 0, '50', 0, 'fs1', 'inbound Gateway1');
INSERT INTO "dispatcher" ("id", "setid", "destination", "socket", "state", "weight", "priority", "attrs", "description") VALUES (2, 1, 'sip:192.168.10.112:5060', NULL, 0, '50', 0, 'fs2', 'inbound Gateway2');
```

#### load_balancer table
```
INSERT INTO "load_balancer" ("id", "group_id", "dst_uri", "resources", "probe_mode", "description") VALUES (1, 1, 'sip:192.168.10.112:5060', 'vm=100;conf=100;transc=100;pstn=500', 1, 'internal FS1');
INSERT INTO "load_balancer" ("id", "group_id", "dst_uri", "resources", "probe_mode", "description") VALUES (2, 1, 'sip:192.168.10.114:5060', 'vm=100;conf=100;transc=100;pstn=500', 1, 'internal FS2');
INSERT INTO "load_balancer" ("id", "group_id", "dst_uri", "resources", "probe_mode", "description") VALUES (4, 3, 'sip:192.168.10.114:5080', 'vm=100;conf=100;transc=100;pstn=500', 1, 'external FS1');
INSERT INTO "load_balancer" ("id", "group_id", "dst_uri", "resources", "probe_mode", "description") VALUES (5, 3, 'sip:192.168.10.112:5080', 'vm=100;conf=100;transc=100;pstn=500', 1, 'external FS2');
```

#### rtpengine table
```
INSERT INTO "rtpengine" ("id", "socket_uri", "set_id") VALUES (1, 'udp:192.168.10.113:7722', 1);
```

### 3. configuration ${freeswitch_home}/conf/dialplan/default.xml
```
<extension name="Local_Extension">
  <condition field="destination_number" expression="^(10[01][0-9])$">
    
    <!--  获取opensips 的 headre 属性 to 呼叫  -->
    <action application="bridge" data="sofia/external/${sip_to_uri}" />
    <action application="sleep" data="1000" />

    <action application="bridge" data="loopback/app=voicemail:default ${domain_name} ${dialed_extension}"/>
  </condition>
</extension>
```
