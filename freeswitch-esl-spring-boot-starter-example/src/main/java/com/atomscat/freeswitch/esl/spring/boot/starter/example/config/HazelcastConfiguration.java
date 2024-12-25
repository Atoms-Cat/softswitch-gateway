package com.atomscat.freeswitch.esl.spring.boot.starter.example.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.NetworkConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {
    @Bean
    public Config hazelcastConfig() {
        Config config = new Config();
        config.setInstanceName("hazelcast-instance");
        NetworkConfig networkConfig = config.getNetworkConfig();

        networkConfig.getJoin().getMulticastConfig().setEnabled(false);
        networkConfig.getJoin().getTcpIpConfig().setEnabled(true)
                .addMember("192.168.62.1")

        ; // 其他节点的 IP 地址

        return config;
    }
}
