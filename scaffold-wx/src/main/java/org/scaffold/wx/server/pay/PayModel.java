package org.scaffold.wx.server.pay;

import com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest;

public interface   PayModel {

    public String getURL();


    //Native下单API
    public PrepayRequest getPrepayRequest(NativeModel nativeModel);
//    微信支付订单号查询
}
