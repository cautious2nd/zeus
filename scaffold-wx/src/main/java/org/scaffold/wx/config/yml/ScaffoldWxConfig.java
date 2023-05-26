package org.scaffold.wx.config.yml;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "wx.server")
public class ScaffoldWxConfig {

    private static final String SERVER_NAME = "scaffold-wx";

    private String message = SERVER_NAME;

    private String version;
    private String token;//

    private String appid;

    private String secret;

    private String merchantId;

    private String privateKeyPath;

    private String merchantSerialNumber;

    private String apiV3key;

    private String payNotifyUrl;//支付回调地址


    public String getPayNotifyUrl() {
        return payNotifyUrl;
    }

    public void setPayNotifyUrl(String payNotifyUrl) {
        this.payNotifyUrl = payNotifyUrl;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public String getMerchantSerialNumber() {
        return merchantSerialNumber;
    }

    public void setMerchantSerialNumber(String merchantSerialNumber) {
        this.merchantSerialNumber = merchantSerialNumber;
    }

    public String getApiV3key() {
        return apiV3key;
    }

    public void setApiV3key(String apiV3key) {
        this.apiV3key = apiV3key;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
