# FreeSWITCH ESL Spring Boot Starter Example

[![Jdk Version](https://img.shields.io/badge/JDK-1.8-green.svg)](https://img.shields.io/badge/JDK-1.8-green.svg)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/link.thingscloud/freeswitch-esl-spring-boot-starter-example/badge.svg)](https://maven-badges.herokuapp.com/maven-central/link.thingscloud/freeswitch-esl-spring-boot-starter-example/)


# Dependent environment

## redis
```shell
docker run -it -d -p 6379:6379 --name redis redis --requirepass "Aa123456"
```

## rabbitmq
```shell
docker run -d -p 14369:4369 -p 5671:5671 -p 5672:5672 -p 15671:15671 -p 15672:15672 -p 15691:15691 -p 15692:15692 -p 25672:25672 -e RABBITMQ_DEFAULT_USER=guest -e RABBITMQ_DEFAULT_PASS=guest --name rabbitmq rabbitmq:3-management 
```
### setting 
```shell

http://127.0.0.1:15672/

```

## nacos
```shell
# download by https://github.com/alibaba/nacos/releases
# unzip package
./bin/startup.sh -m standalone
```

## postgres
```shell
docker run -d \
    --name postgres \
    -p 5432:5432 \
    -e POSTGRES_PASSWORD=Aa123456 \
    -e PGDATA=/var/lib/postgresql/data/pgdata \
    -v /data/postgres/mount:/var/lib/postgresql/data \
    postgres:9.6
```