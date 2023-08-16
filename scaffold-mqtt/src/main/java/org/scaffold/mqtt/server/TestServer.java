package org.scaffold.mqtt.server;

import org.scaffold.mqtt.publish.connect.MqttClientConnect;
import org.scaffold.mqtt.config.yml.MqttPublishConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServer {

    @Autowired
    private MqttPublishConfig mqttPublishConfig;
    @Autowired
    private MqttClientConnect mqttClientConnect;


    public void test() {
        System.out.println(mqttPublishConfig);
    }

}
