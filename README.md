# Opensips And FreeSWITCH For In One

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/3a4aef1c199844c69f273ddd7f0bff16)](https://www.codacy.com/gh/HowellYan/softswitch-gateway/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=HowellYan/softswitch-gateway&amp;utm_campaign=Badge_Grade)
[![Jdk Version](https://img.shields.io/badge/JDK-1.8-green.svg)](https://img.shields.io/badge/JDK-1.8-green.svg)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![codecov](https://codecov.io/gh/HowellYan/softswitch-gateway/branch/main/graph/badge.svg?token=DH9SNP7V5F)](https://codecov.io/gh/HowellYan/softswitch-gateway)

## 目标

    1、支持连接Opensips MI、 Event
    2、支持连接FreeSWITCH ESL(inbound、outbound)、cdr、xml_curl(configuration | directory | dialplan | phrases)
    3、更易于分布式集成方案使用
    4、与spring boot 2.3.x 整合，nacos 配置中心、服务发现
    5、可动态配置

## 模块说明

### 1.[deployment plan](doc)

* [opensips-fs-rtpengine](doc/DeploymentPlan4.md)
* [opensips-register-fs](doc/DeploymentPlan3.md)


### 2.[freeswitch-esl、freeswitch-cdr](https://github.com/zhouhailin)

    参考 https://github.com/zhouhailin/freeswitch-esl-all
    参考 https://github.com/zhouhailin/freeswitch-cdr-all
    参考 https://github.com/zhouhailin/spring-boot-common

### 3.HA Topology Diagram
![](doc/img/opensips-fs-app.png)


## 志同道合(钉钉) - 请备注来源

![微信](doc/img/dingding.JPG)

## License

[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html) Copyright (C) Apache Software Foundation
