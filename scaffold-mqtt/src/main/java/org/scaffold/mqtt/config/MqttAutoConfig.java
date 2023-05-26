package org.scaffold.mqtt.config;

import org.scaffold.mqtt.publish.connect.MqttClientConnect;
import org.scaffold.mqtt.config.yml.MqttPublishConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MqttPublishConfig.class)
@ConditionalOnProperty(prefix = "mqtt", value = "publish", matchIfMissing = false)
public class MqttAutoConfig {


    @Autowired
    private MqttPublishConfig mqttPublishConfig;

    @Bean
    public MqttClientConnect mqttClientConnect(MqttPublishConfig mqttPublishConfig) {
        return new MqttClientConnect(mqttPublishConfig);
    }

}
