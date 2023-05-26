package org.scaffold.mqtt.config.yml;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mqtt.publish")
public class MqttPublishConfig {
    private String host;//  host: tcp:10.30.8.1:1883 #MQTT-服务端地址
    private String username;//  username: admin #MQTT-服务端用户名
    private String password;//password: chiziyue111 #服务端密码
    private boolean cleansession;//  cleansession: false #是否清理session
    private String clientid;//  clientid: mqtt_publish #客户端唯一表示
    private String defaultTopic;// defaultTopic: 测试 #默认主题
    private String timeout;// timeout: 1000 #超时时间
    private int keepalive;// keepalive: 10 #心跳时间
    private int connectionTimeout;// connectionTimeout: 3000 #超时时间

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getCleansession() {
        return cleansession;
    }

    public void setCleansession(boolean cleansession) {
        this.cleansession = cleansession;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getDefaultTopic() {
        return defaultTopic;
    }

    public void setDefaultTopic(String defaultTopic) {
        this.defaultTopic = defaultTopic;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public int getKeepalive() {
        return keepalive;
    }

    public void setKeepalive(int keepalive) {
        this.keepalive = keepalive;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
}
