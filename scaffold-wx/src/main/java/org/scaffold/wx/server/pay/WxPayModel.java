package org.scaffold.wx.server.pay;

import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import org.scaffold.wx.config.yml.ScaffoldWxConfig;
import org.springframework.beans.factory.annotation.Autowired;

import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.Amount;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;

public class WxPayModel implements PayModel {

    private static final String WX_PAY_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/native";


    @Autowired
    private ScaffoldWxConfig wxConfig;


    @Override
    public String getURL() {
        return WX_PAY_URL;
    }

    @Override
    public PrepayRequest getPrepayRequest(NativeModel nativeModel) {


        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal(nativeModel.getAmount().getTotal());
        request.setAmount(amount);
        request.setAppid(wxConfig.getAppid());
        request.setMchid(wxConfig.getMerchantId());
        request.setNotifyUrl(wxConfig.getPayNotifyUrl());
        request.setDescription(nativeModel.getDescription());
        request.setOutTradeNo(nativeModel.getOutTradeNo());


        return request;
    }
}
