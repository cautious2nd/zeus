package org.scaffold.mqtt.subscribe;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.scaffold.logger.log.ScaffoldLogger;
import org.scaffold.mqtt.publish.connect.MqttClientConnect;
import org.scaffold.mqtt.config.yml.MqttPublishConfig;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * MQTT 消息订阅
 */

public class ScaffoldMqttSubscribe {

    private static final ScaffoldLogger SCAFFOLD_LOGGER = new ScaffoldLogger(ScaffoldMqttSubscribe.class);

    private int QOS = 2;
    @Autowired
    private MqttPublishConfig mqttPublishConfig;
    @Autowired
    private MqttClientConnect mqttClientConnect;

    private String topic;


    public ScaffoldMqttSubscribe(MqttPublishConfig mqttPublishConfig, MqttClientConnect mqttClientConnect) throws MqttException {
        this.mqttPublishConfig = mqttPublishConfig;
        this.mqttClientConnect = mqttClientConnect;
    }


    public void subscribe() throws MqttException {
        this.subscribe(this.mqttPublishConfig.getDefaultTopic());
    }

    public void subscribe(String clientid, String topic) throws MqttException {
        MqttClient client = new MqttClient(mqttPublishConfig.getHost(), clientid, new MemoryPersistence());
        // 连接参数
        MqttConnectOptions options = mqttClientConnect.getOptions();

        // 设置回调
        client.setCallback(new MqttCallback() {

            @Override
            public void connectionLost(Throwable cause) {
                cause.printStackTrace();
                try {
                    client.reconnect();
                    System.out.println("重连");
                } catch (MqttException e) {
                    throw new RuntimeException(e);
                }
            }

            /**
             *
             * topic: czy-mqtt
             * Qos: 2
             * message content: aaa
             *
             * @param topic name of the topic on the message was published to
             * @param message the actual message.
             */
            @Override
            public void messageArrived(String topic, MqttMessage message) {
                System.out.println("topic: " + topic);
                System.out.println("Qos: " + message.getQos());
                System.out.println("message content: " + new String(message.getPayload()));

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("deliveryComplete---------" + token.isComplete());
            }


        });
        client.connect(options);
        client.subscribe(topic, QOS);

        SCAFFOLD_LOGGER.info("线程[{}]启动订阅服务,主题是[{}]", Thread.currentThread().getName(), mqttPublishConfig.getDefaultTopic());
    }

    public void subscribe(String topic) throws MqttException {

        MqttClient client = new MqttClient(mqttPublishConfig.getHost(), mqttPublishConfig.getClientid(), new MemoryPersistence());
        // 连接参数
        MqttConnectOptions options = mqttClientConnect.getOptions();

        // 设置回调
        client.setCallback(new MqttCallback() {

            @Override
            public void connectionLost(Throwable cause) {
                cause.printStackTrace();
                try {
                    client.reconnect();
                    System.out.println("重连");
                } catch (MqttException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                System.out.println("topic: " + topic);
                System.out.println("Qos: " + message.getQos());
                System.out.println("message content: " + new String(message.getPayload()));

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("deliveryComplete---------" + token.isComplete());
            }


        });
        client.connect(options);
        client.subscribe(topic, QOS);

        SCAFFOLD_LOGGER.info("线程[{}]启动订阅服务,主题是[{}]", Thread.currentThread().getName(), mqttPublishConfig.getDefaultTopic());

    }


}
