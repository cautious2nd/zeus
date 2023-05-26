package org.scaffold.mqtt.publish;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.scaffold.mqtt.publish.connect.MqttClientConnect;
import org.scaffold.mqtt.config.yml.MqttPublishConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class ScaffoldMqttPublish {

    private static int QOS = 2;

    @Autowired
    private MqttPublishConfig mqttPublishConfig;
    @Autowired
    private MqttClientConnect mqttClientConnect;

    public void setQOS(int QOS) {
        ScaffoldMqttPublish.QOS = QOS;
    }

//    public MqttClient getMqttClient() throws MqttException {
//        MqttClient mqttClient = new MqttClient(mqttPublishConfig.getHost(), mqttPublishConfig.getClientid());
//        mqttClient.connect(mqttClientConnect.getOptions());
//        return mqttClient;
//    }

    public void sendMessage(String message) throws MqttException {
        sendMessage(mqttPublishConfig.getDefaultTopic(), message);
    }

    public void sendMessage(String topic, String message) throws MqttException {

        MqttClient mqttClient = new MqttClient(mqttPublishConfig.getHost(), mqttPublishConfig.getClientid());
        try {
            mqttClient.connect(mqttClientConnect.getOptions());

            MqttMessage mqttMessage = new MqttMessage(message.getBytes(StandardCharsets.UTF_8));
            mqttMessage.setQos(QOS);

            mqttClient.publish(topic, mqttMessage);
        } finally {
            mqttClient.disconnect();
            mqttClient.close();
        }


    }


}
