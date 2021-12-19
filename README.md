# SoftSwitch-Gateway : Opensips And FreeSWITCH For In One

Based on netty 4 docking Opensips Exported Event, Exported MI and Freeswitch Event Socket Library, cdr, xml_curl and
other interface implementation solutions

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/74f187c0039a40dd9f044c4def3517d2)](https://www.codacy.com/gh/Atoms-Cat/softswitch-gateway/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Atoms-Cat/softswitch-gateway&amp;utm_campaign=Badge_Grade)
[![Jdk Version](https://img.shields.io/badge/JDK-1.8-green.svg)](https://img.shields.io/badge/JDK-1.8-green.svg)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![codecov](https://codecov.io/gh/HowellYan/softswitch-gateway/branch/main/graph/badge.svg?token=DH9SNP7V5F)](https://codecov.io/gh/HowellYan/softswitch-gateway)

## Target
```text
1. Support to connect to Opensips MI and events
2. Support connection to FreeSWITCH ESL (inbound, outbound), cdr, xml_curl (configuration(ivr)|directory|dial plan|phrase)
3. Easier to use integrated solutions
4. Integrate with spring boot 2.3.x, nacos configuration center, service discovery
5. Can be dynamically configured
```

---

## Illustrate

### 1.[deployment plan](doc)

* [opensips-fs-tandem](doc/DeploymentPlan1.md)
* [opensips-register-fs](doc/DeploymentPlan3.md)
* [opensips-fs-rtpengine](doc/DeploymentPlan4.md) (recommend)

### 2.[freeswitch-esl、freeswitch-cdr](https://github.com/zhouhailin)

    refer to https://github.com/zhouhailin/freeswitch-esl-all
    refer to https://github.com/zhouhailin/freeswitch-cdr-all
    refer to https://github.com/zhouhailin/spring-boot-common

### 3.[freeswitch-xml-curl](freeswitch-xml-curl/README.md)

http url path:

    /freeswitch/xml/curl

example: ${freeswitch_home}/conf/autoload_configs/xml_curl.conf.xml

```xml
<binding name="all configs">
    <param name="gateway-url" value="http://127.0.0.1:8080/freeswitch/xml/curl" bindings="dialplan|configuration|directory|phrases"/>
    <param name="method" value="GET"/>
</binding>
```


### 4.HA Topology Diagram

![](doc/img/opensips-fs-app.png)

---

## Like-minded (dingtalk)-Please note the source

![微信](doc/img/dingding.JPG)

## License

[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html) Copyright (C) Apache Software Foundation
