### 部署中央仓库
```
mvn clean package -DskipTests deploy -projects freeswitch-cdr
mvn clean package -DskipTests deploy -projects freeswitch-esl
mvn clean package -DskipTests deploy -projects freeswitch-xml-curl
mvn clean package -DskipTests deploy -projects opensips-event-mi
mvn clean package -DskipTests deploy -projects spring-boot-common/spring-boot-common-aop,spring-boot-common/spring-boot-common-core
mvn clean package -DskipTests deploy -projects freeswitch-cdr-spring-boot-starter,freeswitch-esl-spring-boot-starter,freeswitch-xml-curl-spring-boot-starter,opensips-event-mi-spring-boot-starter
```