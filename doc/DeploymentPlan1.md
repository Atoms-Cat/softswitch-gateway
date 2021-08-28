## opensips sip 代理 freeswitch  汇接

参考：https://freeswitch.org/confluence/display/FREESWITCH/Enterprise+deployment+OpenSIPS

![流程图](img/opensips-fs-tandem.png)


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

debug_mode=no

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

# listen=udp:127.0.0.1:5060     # CUSTOMIZE ME
listen=udp:192.168.10.109:5060  # CUSTOMIZE ME

####### Modules Section ########

#set module path
mpath="/tools/install/opensips/lib64/opensips/modules/" # CUSTOMIZE ME

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

#### USeR LOCation module
loadmodule "usrloc.so"
modparam("usrloc", "nat_bflag", "NAT")
modparam("usrloc", "working_mode_preset", "single-instance-no-db")

#### REGISTRAR module
loadmodule "registrar.so"
modparam("registrar", "tcp_persistent_flag", "TCP_PERSISTENT")
/* uncomment the next line not to allow more than 10 contacts per AOR */
#modparam("registrar", "max_contacts", 10)

#### ACCounting module
loadmodule "acc.so"
/* what special events should be accounted ? */
modparam("acc", "early_media", 0)
modparam("acc", "report_cancels", 0)
/* by default we do not adjust the direct of the sequential requests.
   if you enable this parameter, be sure to enable "append_fromtag"
   in "rr" module */
modparam("acc", "detect_direction", 0)

loadmodule "proto_udp.so"

#### pgsql dsn 数据源 模块
loadmodule "db_postgres.so"


#### load_balancer 负载均衡 模块
loadmodule "load_balancer.so"
loadmodule "dialog.so"
modparam("load_balancer", "db_url", "postgres://opensips:123456@192.168.10.114/opensips")  # CUSTOMIZE ME
modparam("load_balancer", "probing_method", "INFO")
modparam("load_balancer", "probing_interval", 5)


#### dispatcher 调度 模块
loadmodule "dispatcher.so"
modparam("dispatcher", "ds_ping_method", "INFO")
modparam("dispatcher", "ds_ping_interval", 3)
modparam("dispatcher", "ds_probing_threshhold", 2)
modparam("dispatcher", "ds_probing_mode", 1)
modparam("dispatcher", "db_url", "postgres://opensips:123456@192.168.10.114/opensips")

#### Freeswitch 模块
loadmodule "freeswitch.so"

loadmodule "topology_hiding.so"

####### Routing Logic ########

# main request routing logic

route{

   if (!mf_process_maxfwd_header("10")) {
        sl_send_reply("483","Too Many Hops");
        exit;
   }

   if (!has_totag()) {
        record_route();
   } else {
        loose_route();
        t_relay();
        exit;
   }

    if (is_method("CANCEL")) {
        if ( t_check_trans() )
                t_relay();
        exit;
    }

    if (is_method("INVITE")) {
       
        # if ( !create_dialog("B") ) {
        #        send_reply("500","Internal Server Error");
        #        exit;
        # }
              
        #if (!load_balance("1", "pstn", "1")) {
        if (!lb_start_or_next("1","pstn","ns")) {   # CUSTOMIZE ME
                send_reply("503","Service Unavailable");
                exit;
        }

        xlog("sending call to $du\n");
        
       #  topology_hiding("UC");
        
    } else if (is_method("REGISTER")) {
        if (!ds_select_dst("2", "4")) { # CUSTOMIZE ME
                send_reply("503","Service Unavailable");
                exit;
        }
    } else {
        send_reply("405","Method Not Allowed");
        exit;
    }

    if (!t_relay()) {
        sl_reply_error();
    }
}
```


### 2.opensips数据库
#### dispatcher table # CUSTOMIZE ME
```
INSERT INTO "public"."dispatcher" ("id", "setid", "destination", "socket", "state", "weight", "priority", "attrs", "description") VALUES (1, 1, 'sip:192.168.10.114:5060', '', 0, '50', 0, 'fs1', 'inbound Gateway1');
INSERT INTO "public"."dispatcher" ("id", "setid", "destination", "socket", "state", "weight", "priority", "attrs", "description") VALUES (2, 1, 'sip:192.168.10.112:5060', NULL, 0, '50', 0, 'fs2', 'inbound Gateway2');
INSERT INTO "public"."dispatcher" ("id", "setid", "destination", "socket", "state", "weight", "priority", "attrs", "description") VALUES (3, 2, 'sip:192.168.10.109:25060', NULL, 0, '50', 0, 'fs3', 'Outbound Gateway1');
```

#### load_balancer table # CUSTOMIZE ME
```
INSERT INTO "public"."load_balancer" ("id", "group_id", "dst_uri", "resources", "probe_mode", "description") VALUES (1, 1, 'sip:192.168.10.112:5060', 'vm=100;conf=100;transc=100;pstn=500', 1, 'FS1');
INSERT INTO "public"."load_balancer" ("id", "group_id", "dst_uri", "resources", "probe_mode", "description") VALUES (2, 1, 'sip:192.168.10.114:5060', 'vm=100;conf=100;transc=100;pstn=500', 1, 'FS1');
INSERT INTO "public"."load_balancer" ("id", "group_id", "dst_uri", "resources", "probe_mode", "description") VALUES (3, 1, 'sip:192.168.10.109:25060', 'vm=100;conf=100;transc=100;pstn=500', 1, 'FS1');
```

### 3.fs-A、fs-B 的 freeswitch配置  /conf/dialplan/default.xml # CUSTOMIZE ME
```
<extension name="meeting">
  <condition field="destination_number" expression="^(10[01][0-9])$">
    <action application="bridge" data="sofia/external/sip:$1@192.168.10.109:5060" /> <!-- Notice: this is pass FS-REGISTER ip # CUSTOMIZE ME -->
  </condition>
</extension>

<extension name="public_extensions">
  <condition field="destination_number" expression="^(10[01][0-9])$">
    <action application="transfer" data="$1 XML default"/>
  </condition>
</extension>
```
