## oepnsips load balancing freeswitch

![流程图](img/opensips-register-fs.png)

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

# loadmodule "rtpproxy.so"
# modparam("rtpproxy", "rtpproxy_sock", "udp:192.168.10.113:12221") # CUSTOMIZE ME

####  DIALPLAN module
loadmodule "dialplan.so"
modparam("dialplan", "db_url","postgres://opensips:123456@192.168.10.114/opensips") # CUSTOMIZE ME

####  DYNAMMIC ROUTING module
loadmodule "drouting.so"
modparam("drouting", "db_url","postgres://opensips:123456@192.168.10.114/opensips") # CUSTOMIZE ME

####  MI_HTTP module
loadmodule "mi_http.so"

loadmodule "proto_udp.so"

loadmodule "proto_ws.so"
modparam("proto_ws", "ws_port", 5062)

loadmodule "proto_udp.so"
loadmodule "proto_tcp.so"
# loadmodule "proto_tls.so"

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
#       if (!is_method("REGISTER|MESSAGE"))
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

                # FS to OpenSips 代理外呼
                if (lb_is_destination("$si","$sp","3") ) {
                    xlog("FS TO OPENSIPS");
                    if (!lookup("location", "m")) {
                         t_reply("404", "Not Found");
                         exit;
                    }
                    
                } else {
                    # 呼入到 FS 
                    xlog("OPENSIPS TO FS");
                    if (!lb_start_or_next("1","pstn","ns")) {
                        send_reply("500","Internal Server Error");
                        exit;
                    }
                }
                $avp(received_nh) = $ru;
                xlog("$du , $ru");
                do_accounting("db");
        }


        if (!is_uri_host_local()) {
                append_hf("P-hint: outbound\r\n");

                # if you have some interdomain connections via TLS
                ## CUSTOMIZE IF NEEDED
                ##if ($rd=="tls_domain1.net"
                ## || $rd=="tls_domain2.net"
                ##) {
                ##      force_send_socket(tls:127.0.0.1:5061); # CUSTOMIZE
                ##}

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
                    if(!save("location","f")) {
                        xlog(" ws registerer error");
                    }
                } else {
                    if (!save("location")) {
                        xlog("failed to register AoR $tu\n");
                    }
                }
                xlog("$fd:$aU  $ct.fields(uri) ");

                cache_store("redis","$fd:$au","$ct.fields(uri)");

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

                if (isflagset(NAT)) {
#                   rtpproxy_offer("ro");
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
if (nat_uac_test("1")){
fix_nated_contact();
xlog(" reply route: $du $ru $avp(received_nh)");
}
#       if ( isflagset(NAT) )
#               rtpproxy_answer("ro");
        xlog("incoming reply\n");
}


failure_route[missed_call] {
if (t_was_cancelled()) {
exit;
}

        # uncomment the following lines if you want to block client
        # redirect based on 3xx replies.
        ##if (t_check_status("3[0-9][0-9]")) {
        ##t_reply("404","Not found");
        ##      exit;
        ##}

        # redirect the failed to a different VM system
        if (t_check_status("486|408")) {
                # $du = "sip:192.168.10.112:5060"; # CUSTOMIZE ME
                # do not set the missed call flag again
                route(relay);
        }
}

local_route {
    if (is_method("BYE") && $DLG_dir=="UPSTREAM") {
        acc_db_request("200 Dialog Timeout", "acc");
    }
}
```

### 2.fs 拨号计划

```
<extension name="Local_Extension">
      <condition field="destination_number" expression="^(10[01][0-9])$">
        <action application="export" data="dialed_extension=$1"/>
        <!-- bind_meta_app can have these args <key> [a|b|ab] [a|b|o|s] <app> -->
        <action application="bind_meta_app" data="1 b s execute_extension::dx XML features"/>
        <action application="bind_meta_app" data="2 b s record_session::$${recordings_dir}/${caller_id_number}.${strftime(%Y-%m-%d-%H-%M-%S)}.wav"/>
        <action application="bind_meta_app" data="3 b s execute_extension::cf XML features"/>
        <action application="bind_meta_app" data="4 b s execute_extension::att_xfer XML features"/>
        <action application="set" data="ringback=${us-ring}"/>
        <action application="set" data="transfer_ringback=$${hold_music}"/>
        <action application="set" data="call_timeout=30"/>
        <!-- <action application="set" data="sip_exclude_contact=${network_addr}"/> -->
        <action application="set" data="hangup_after_bridge=true"/>
        <!--<action application="set" data="continue_on_fail=NORMAL_TEMPORARY_FAILURE,USER_BUSY,NO_ANSWER,TIMEOUT,NO_ROUTE_DESTINATION"/> -->
        <action application="set" data="continue_on_fail=true"/>
        <action application="hash" data="insert/${domain_name}-call_return/${dialed_extension}/${caller_id_number}"/>
        <action application="hash" data="insert/${domain_name}-last_dial_ext/${dialed_extension}/${uuid}"/>
        <action application="set" data="called_party_callgroup=${user_data(${dialed_extension}@${domain_name} var callgroup)}"/>
        <action application="hash" data="insert/${domain_name}-last_dial_ext/${called_party_callgroup}/${uuid}"/>
        <action application="hash" data="insert/${domain_name}-last_dial_ext/global/${uuid}"/>
        <!-- <action application="export" data="nolocal:rtp_secure_media=${user_data(${dialed_extension}@${domain_name} var rtp_secure_media)}"/>-->
        <action application="hash" data="insert/${domain_name}-last_dial/${called_party_callgroup}/${uuid}"/>

        <action application="answer"/>
        <action application="sleep" data="1000"/>

        <!-- get opensips sip header To -->
        <action application="bridge" data="sofia/external/${sip_to_uri}" />
        <action application="sleep" data="1000" />
        <action application="bridge" data="loopback/app=voicemail:default ${domain_name} ${dialed_extension}"/>
      </condition>
    </extension>
```

### 3.opensips数据库

#### dispatcher table # CUSTOMIZE ME

```
INSERT INTO "dispatcher" ("id", "setid", "destination", "socket", "state", "weight", "priority", "attrs", "description") VALUES (1, 1, 'sip:192.168.10.114:5060', '', 0, '50', 0, 'fs1', 'inbound Gateway1');
INSERT INTO "dispatcher" ("id", "setid", "destination", "socket", "state", "weight", "priority", "attrs", "description") VALUES (2, 1, 'sip:192.168.10.112:5060', '', 0, '50', 0, 'fs2', 'inbound Gateway2');
```

#### load_balancer table # CUSTOMIZE ME

```
INSERT INTO "load_balancer" ("id", "group_id", "dst_uri", "resources", "probe_mode", "description") VALUES (1, 1, 'sip:192.168.10.112:5060', 'vm=100;conf=100;transc=100;pstn=500', 1, 'internal FS1');
INSERT INTO "load_balancer" ("id", "group_id", "dst_uri", "resources", "probe_mode", "description") VALUES (2, 1, 'sip:192.168.10.114:5060', 'vm=100;conf=100;transc=100;pstn=500', 1, 'internal FS2');
INSERT INTO "load_balancer" ("id", "group_id", "dst_uri", "resources", "probe_mode", "description") VALUES (4, 3, 'sip:192.168.10.114:5080', 'vm=100;conf=100;transc=100;pstn=500', 1, 'external FS1');
INSERT INTO "load_balancer" ("id", "group_id", "dst_uri", "resources", "probe_mode", "description") VALUES (5, 3, 'sip:192.168.10.112:5080', 'vm=100;conf=100;transc=100;pstn=500', 1, 'external FS2');
```



