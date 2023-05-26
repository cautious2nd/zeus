package org.scaffold.wx.server.pay;

/**
 * 场景信息
 */
public class SceneInfoModel {

    private String payerClientIp;//   用户终端IP	payer_client_ip	string[1,45]	是	用户的客户端IP，支持IPv4和IPv6两种格式的IP地址。 示例值：14.23.150.211
    private String deviceId;//   商户端设备号	device_id	string[1,32]	否	商户端设备号（门店号或收银设备ID）。示例值：013467007045764
    private StoreInfoModel storeInfo;//   +商户门店信息	store_info	object	否	商户门店信息

    public String getPayerClientIp() {
        return payerClientIp;
    }

    public void setPayerClientIp(String payerClientIp) {
        this.payerClientIp = payerClientIp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public StoreInfoModel getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfoModel storeInfo) {
        this.storeInfo = storeInfo;
    }
}
