## Debian 10 编译安装 freeswitch 1.10.5

#### similar soft： OpenSER、Asterisk、Kamailio

### 参考：

https://freeswitch.org/confluence/display/FREESWITCH/Debian+10+Buster

### 环境

Debian 10.10

```shell
# 自2022年3月11日起，通过源编译源码安装signalwire，Signalwire账号登录配置TOKEN
# 参考：https://freeswitch.org/confluence/display/FREESWITCH/HOWTO+Create+a+SignalWire+Personal+Access+Token

apt-get update -y 
apt-get install -y sngrep vim gnupg2 wget lsb-release

# pat_1X8EQXH6EvgajWaBVWSJCG51   是在Signalwire网站上注册账号配置的TOKEN
wget --http-user=signalwire --http-password=pat_1X8EQXH6EvgajWaBVWSJCG51 -O /usr/share/keyrings/signalwire-freeswitch-repo.gpg https://freeswitch.signalwire.com/repo/deb/debian-release/signalwire-freeswitch-repo.gpg
echo "machine freeswitch.signalwire.com login signalwire password pat_1X8EQXH6EvgajWaBVWSJCG51" > /etc/apt/auth.conf
echo "deb [signed-by=/usr/share/keyrings/signalwire-freeswitch-repo.gpg] https://freeswitch.signalwire.com/repo/deb/debian-release/ `lsb_release -sc` main" > /etc/apt/sources.list.d/freeswitch.list
echo "deb-src [signed-by=/usr/share/keyrings/signalwire-freeswitch-repo.gpg] https://freeswitch.signalwire.com/repo/deb/debian-release/ `lsb_release -sc` main" >> /etc/apt/sources.list.d/freeswitch.list
 
# Install dependencies required for the build
apt-get update -y && apt-get build-dep freeswitch -y
```

### 编译安装freeswitch

``` shell
mkdir -p /tools/software && cd /tools/software
wget https://github.com/signalwire/freeswitch/archive/refs/tags/v1.10.5.tar.gz
tar -zxvf  v1.10.5.tar.gz

cd freeswitch-1.10.5/

# 启动需要的模块，修改源码根目录：modules.conf 文件
# 例如：将呼叫详细记录 (CDR) 直接记录到 PostgreSQL 数据库，参考：https://freeswitch.org/confluence/display/FREESWITCH/mod_cdr_pg_csv
event_handlers/mod_cdr_pg_csv

# fs 操作 redis 
applications/mod_hiredis

# 动态configuration(ivr、external-gateway)、directory、dialplan、phrases
xml_int/mod_xml_curl


./bootstrap.sh -j
./configure
make
make install
```

### 相关目录

```text
-------------------------- FreeSWITCH configuration --------------------------
Locations:
prefix: /usr/local/freeswitch
exec_prefix: /usr/local/freeswitch
bindir: ${exec_prefix}/bin
confdir: /usr/local/freeswitch/conf
libdir: ${exec_prefix}/lib
datadir: /usr/local/freeswitch
localstatedir: /usr/local/freeswitch
includedir: /usr/local/freeswitch/include/freeswitch
certsdir: /usr/local/freeswitch/certs
dbdir: /usr/local/freeswitch/db
grammardir: /usr/local/freeswitch/grammar
htdocsdir: /usr/local/freeswitch/htdocs
fontsdir: /usr/local/freeswitch/fonts
logfiledir: /usr/local/freeswitch/log
modulesdir: /usr/local/freeswitch/mod
pkgconfigdir: ${exec_prefix}/lib/pkgconfig
recordingsdir: /usr/local/freeswitch/recordings
imagesdir: /usr/local/freeswitch/images
runtimedir: /usr/local/freeswitch/run
scriptdir: /usr/local/freeswitch/scripts
soundsdir: /usr/local/freeswitch/sounds
storagedir: /usr/local/freeswitch/storage
cachedir: /usr/local/freeswitch/cache
------------------------------------------------------------------------------
```

### 启动

* 改bind ip

```xml
<!-- ${fs_home}/conf/vars.xml增加如下两行，force_local_ip_v4后改成你需要绑定的ip -->
<X-PRE-PROCESS cmd="set" data="bind_server_ip=auto"/>
```

* 启动

```shell
cd /usr/local/freeswitch
./freeswitch

# 可以看到详细启动日志,启动后进入控制台模式，退出使用shutdown
./freeswitch -c -nonat -rp

# 默认后台运行
./freeswitch -nc ***

# 生成环境后台运行
./freeswitch -rp -nc -nonat

# 关闭
./freeswitch -stop ***


# 查看注册用户在线情况
sofia status profile internal reg

# 指定配置启动
./freeswitch -ncwait -conf /usr/local/ops/freeswitch_node/ -db /usr/local/ops/freeswitch_node/db/ -log /usr/local/ops/freeswitch_node/log/
```


