package org.scaffold.mqtt.publish.connect;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.scaffold.mqtt.config.yml.MqttPublishConfig;

/**
 * MQTT 服务端连接
 * tcp端口默认1883
 * ssl端口默认8883
 */
public class MqttClientConnect {

    private MqttPublishConfig mqttPublishConfig;


    public MqttClientConnect(MqttPublishConfig mqttPublishConfig) {
        this.mqttPublishConfig = mqttPublishConfig;
    }

    /**
     * MqttConnectOptions
     * 支持setServerURIs
     *
     * @return
     */
    public MqttConnectOptions getOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(mqttPublishConfig.getCleansession());
        mqttConnectOptions.setUserName(mqttPublishConfig.getUsername());
        mqttConnectOptions.setPassword(mqttPublishConfig.getPassword().toCharArray());
        mqttConnectOptions.setConnectionTimeout(mqttPublishConfig.getConnectionTimeout());
        mqttConnectOptions.setKeepAliveInterval(mqttPublishConfig.getKeepalive());
        return mqttConnectOptions;
    }

}
