```
 if (is_method("INVITE")) {
                #if (!lookup("location","m")) {
                #}
                
                xlog("si: $si, sp: $sp, ru: $ru, received: $Ri:$Rp");

                # remove_hf("To");
                # append_hf("To: $ru\r\n", "From");     

                # create dialog with timeout
                if ( !create_dialog("B") ) {
                        send_reply("500","Internal Server Error");
                        exit;
                } 
                 
                if (lb_is_destination("$si","$sp","3") ) {
                    # 外呼到语音网关
                    xlog("FS TO OPENSIPS  $(hdr(X-Voice-Gateway)[0])");
                    if ($(hdr(X-Voice-Gateway)[0]) != NULL && $(hdr(X-Voice-Gateway)[0]) == "true") {
                        if (!lb_start_or_next("4","pstn","ns")) {
                            send_reply("500","Internal Server Error");
                            exit;
                        }                       

                    } else if (!lookup("location", "m")) {
                    # 外出 到 其他的 sip 地址
                        t_reply("404", "Not Found");
                        exit;
                    }
                    
                } else {
                    # 呼入到 FS
                    xlog("OPENSIPS TO FS, set_id: $hdr(X-set-id)");
                    if (!lb_start_or_next("1","pstn","ns")) {
                        send_reply("500","Internal Server Error");
                        exit;
                    }

                    #if (search("User-Agent: PhonerLite*")) {
                    #    remove_hf("Contact");
                    #    append_hf("Contact: $hdr(Contact);received=\"sip:$Ri:$Rp\"\r\n", "CSeq");
                    #}


                }
                # $avp(received_nh) = $ru;

                xlog("$du , $ru ");
                
               # rtpproxy_engage(,,"1", "$var(proxy)");
               # xlog("SCRIPT: RTPProxy server used is $var(proxy)\n");
                        
                do_accounting("db");
        }
```