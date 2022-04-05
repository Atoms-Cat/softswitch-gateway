## 构建 freeswitch 1.10.7 的 docker镜像

### 环境准备
```
 docker 19 版本以上的环境、freeswitch源码
```

### 下载源码
```shell
# 自2022年3月11日起，通过源编译源码安装signalwire，Signalwire账号登录配置TOKEN
# 参考：https://freeswitch.org/confluence/display/FREESWITCH/HOWTO+Create+a+SignalWire+Personal+Access+Token

# 下载
wget https://github.com/signalwire/freeswitch/archive/refs/tags/v1.10.7.tar.gz
# 解压
tar -zxvf  v1.10.7.tar.gz
```

### 编写 Dockerfile 脚本
```dockerfile
FROM debian:11.1
# 参考 https://freeswitch.org/confluence/display/FREESWITCH/Debian+11+Bullseye
# 安装编译freeswitch，所需要依赖环境
RUN apt-get update -y 
RUN apt-get install -y sngrep vim gnupg2 wget lsb-release

# pat_1X8EQXH6EvgajWaBVWSJCG51   是在Signalwire网站上注册账号配置的TOKEN
RUN wget --http-user=signalwire --http-password=pat_1X8EQXH6EvgajWaBVWSJCG51 -O /usr/share/keyrings/signalwire-freeswitch-repo.gpg https://freeswitch.signalwire.com/repo/deb/debian-release/signalwire-freeswitch-repo.gpg
RUN echo "machine freeswitch.signalwire.com login signalwire password pat_1X8EQXH6EvgajWaBVWSJCG51" > /etc/apt/auth.conf
RUN echo "deb [signed-by=/usr/share/keyrings/signalwire-freeswitch-repo.gpg] https://freeswitch.signalwire.com/repo/deb/debian-release/ `lsb_release -sc` main" > /etc/apt/sources.list.d/freeswitch.list
RUN echo "deb-src [signed-by=/usr/share/keyrings/signalwire-freeswitch-repo.gpg] https://freeswitch.signalwire.com/repo/deb/debian-release/ `lsb_release -sc` main" >> /etc/apt/sources.list.d/freeswitch.list
 
RUN apt-get update -y && apt-get build-dep freeswitch -y
RUN mkdir -p /tools

# 需要把下载freeswitch-1.10.7.tar.gz，解压到Dockerfile文件同级目录下，添加到docker镜像里
ADD ./freeswitch-1.10.7 /tools/freeswitch

RUN cd /tools/freeswitch && ./bootstrap.sh -j

# 自定义安装freeswitch模块
COPY ./modules.conf /tools/freeswitch/modules.conf
RUN cd /tools/freeswitch && ./configure

# 安装默认音频
RUN cd /tools/freeswitch && make cd-sounds-install cd-moh-install
RUN cd /tools/freeswitch && make -j && make all install

# 添加启动脚本
ADD ./start.sh /start.sh
RUN chmod +x /start.sh
SHELL       ["/bin/bash"]
HEALTHCHECK --interval=15s --timeout=5s \
    CMD  /usr/local/freeswitch/bin/fs_cli -x status | grep -q ^UP || exit 1
CMD ["/start.sh"]
```

### 容器启动脚本: start.sh 
```shell
#!/bin/bash
set -e
exec /usr/local/freeswitch/bin/freeswitch
```

### 自定义需要加载安装的freeswitch模块: modules.conf
```text
#applications/mod_abstraction
applications/mod_av
#applications/mod_avmd
#applications/mod_bert
#applications/mod_blacklist
#applications/mod_callcenter
#applications/mod_cidlookup
#applications/mod_cluechoo
applications/mod_commands
applications/mod_conference

# action 2021-12-17
applications/mod_curl

#applications/mod_cv
applications/mod_db
#applications/mod_directory
#applications/mod_distributor
applications/mod_dptools
#applications/mod_easyroute
applications/mod_enum
applications/mod_esf

# action 2021-12-17
applications/mod_esl

applications/mod_expr
applications/mod_fifo
#applications/mod_fsk
applications/mod_fsv
applications/mod_hash

# action 2021-12-17
applications/mod_hiredis

applications/mod_httapi
#applications/mod_http_cache
#applications/mod_ladspa
#applications/mod_lcr
#applications/mod_memcache
#applications/mod_mongo
#applications/mod_mp4
#applications/mod_mp4v2
#applications/mod_nibblebill
#applications/mod_oreka
#applications/mod_osp
#applications/mod_prefix
#applications/mod_rad_auth
#applications/mod_redis
#applications/mod_rss

# action 2021-12-18
#applications/mod_signalwire

applications/mod_sms
#applications/mod_sms_flowroute
#applications/mod_snapshot
#applications/mod_snom
#applications/mod_sonar
#applications/mod_soundtouch
applications/mod_spandsp
#applications/mod_spy
#applications/mod_stress
applications/mod_test
#applications/mod_translate
applications/mod_valet_parking
#applications/mod_video_filter
#applications/mod_vmd
applications/mod_voicemail
#applications/mod_voicemail_ivr
#asr_tts/mod_cepstral
#asr_tts/mod_flite
#asr_tts/mod_pocketsphinx
#asr_tts/mod_tts_commandline
#asr_tts/mod_unimrcp
codecs/mod_amr
#codecs/mod_amrwb
codecs/mod_b64
#codecs/mod_bv
#codecs/mod_clearmode
#codecs/mod_codec2
#codecs/mod_com_g729
#codecs/mod_dahdi_codec
codecs/mod_g723_1
codecs/mod_g729
codecs/mod_h26x
#codecs/mod_ilbc
#codecs/mod_isac
#codecs/mod_mp4v
codecs/mod_opus
#codecs/mod_sangoma_codec
#codecs/mod_silk
#codecs/mod_siren
#codecs/mod_theora
#databases/mod_mariadb
databases/mod_pgsql
dialplans/mod_dialplan_asterisk
#dialplans/mod_dialplan_directory
dialplans/mod_dialplan_xml
#directories/mod_ldap
#endpoints/mod_alsa
#endpoints/mod_dingaling
#endpoints/mod_gsmopen
#endpoints/mod_h323
#endpoints/mod_khomp
endpoints/mod_loopback
#endpoints/mod_opal
#endpoints/mod_portaudio
endpoints/mod_rtc
#endpoints/mod_rtmp
endpoints/mod_skinny
endpoints/mod_sofia
endpoints/mod_verto
#event_handlers/mod_amqp
event_handlers/mod_cdr_csv
#event_handlers/mod_cdr_mongodb

# action 2021-12-17 
event_handlers/mod_cdr_pg_csv

event_handlers/mod_cdr_sqlite
#event_handlers/mod_erlang_event
#event_handlers/mod_event_multicast
event_handlers/mod_event_socket
#event_handlers/mod_fail2ban
#event_handlers/mod_format_cdr
#event_handlers/mod_json_cdr
#event_handlers/mod_radius_cdr
#event_handlers/mod_odbc_cdr
#event_handlers/mod_kazoo
#event_handlers/mod_rayo
#event_handlers/mod_smpp
#event_handlers/mod_snmp
#event_handlers/mod_event_zmq
#formats/mod_imagick
formats/mod_local_stream
formats/mod_native_file
formats/mod_png
#formats/mod_portaudio_stream
#formats/mod_shell_stream
#formats/mod_shout
formats/mod_sndfile
#formats/mod_ssml
formats/mod_tone_stream
#formats/mod_vlc
#formats/mod_opusfile
#languages/mod_basic
#languages/mod_java
languages/mod_lua
#languages/mod_managed
#languages/mod_perl
#languages/mod_python
#languages/mod_python3
#languages/mod_v8
#languages/mod_yaml
loggers/mod_console
#loggers/mod_graylog2
loggers/mod_logfile
loggers/mod_syslog
#loggers/mod_raven
#say/mod_say_de
say/mod_say_en
#say/mod_say_es
#say/mod_say_es_ar
#say/mod_say_fa
#say/mod_say_fr
#say/mod_say_he
#say/mod_say_hr
#say/mod_say_hu
#say/mod_say_it
#say/mod_say_ja
#say/mod_say_nl
#say/mod_say_pl
#say/mod_say_pt
#say/mod_say_ru
#say/mod_say_sv
#say/mod_say_th
#say/mod_say_zh
#timers/mod_posix_timer
#timers/mod_timerfd
xml_int/mod_xml_cdr

# action 2021-12-17
xml_int/mod_xml_curl

#xml_int/mod_xml_ldap
#xml_int/mod_xml_radius
xml_int/mod_xml_rpc
xml_int/mod_xml_scgi

#mod_freetdm|https://github.com/freeswitch/freetdm.git -b master

## Experimental Modules (don't cry if they're broken)
#../../contrib/mod/xml_int/mod_xml_odbc
```

### 构建镜像
```shell
docker build -t freeswitch-1.10.7 .
```

### 启动容器
```shell
docker run -it --network host --name freeswitch  -v /data/freeswitch/conf:/usr/local/freeswitch/conf -d freeswitch-1.10.7
```