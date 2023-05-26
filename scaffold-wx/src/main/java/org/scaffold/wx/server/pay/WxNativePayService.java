package org.scaffold.wx.server.pay;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;

import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.model.*;
import org.scaffold.common.json.GsonUtils;
import org.scaffold.wx.config.yml.ScaffoldWxConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class WxNativePayService {
    @Autowired
    private ScaffoldWxConfig wxConfig;

    @Autowired
    private NativePayService nativePayService;
    @Autowired
    private RSAAutoCertificateConfig wxPayConfig;

    /**
     * 关闭订单
     */
    @RequestMapping("closeOrder")
    public void closeOrder() {

        CloseOrderRequest request = new CloseOrderRequest();
        // 调用request.setXxx(val)设置所需参数，具体参数可见Request定义
        // 调用接口
        nativePayService.closeOrder(request);
    }

    /**
     * Native支付预下单
     */
    @RequestMapping("prepay")
    public PrepayResponse prepay(Integer total, String desc, String orderNo, String attach) {

        // 调用request.setXxx(val)设置所需参数，具体参数可见Request定义
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal(total);
        request.setAmount(amount);
        request.setDescription(desc);
        request.setOutTradeNo(orderNo);

        request.setAttach(attach);

        request.setAppid(wxConfig.getAppid());
        request.setMchid(wxConfig.getMerchantId());
        request.setNotifyUrl(wxConfig.getPayNotifyUrl());
        // 调用接口
        return nativePayService.prepay(request);
    }

    /**
     * 微信支付订单号查询订单
     */
    @RequestMapping("queryOrderById")
    public Transaction queryOrderById(String transactionId) {

        QueryOrderByIdRequest request = new QueryOrderByIdRequest();
        // 调用request.setXxx(val)设置所需参数，具体参数可见Request定义

        request.setMchid(wxConfig.getMerchantId());
        request.setTransactionId(transactionId);

        // 调用接口
        return nativePayService.queryOrderById(request);
    }

    /**
     * 商户订单号查询订单
     */
    @RequestMapping("queryOrderByOutTradeNo")
    public Transaction queryOrderByOutTradeNo(String outTradeNo) {

        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        // 调用request.setXxx(val)设置所需参数，具体参数可见Request定义
        request.setMchid(wxConfig.getMerchantId());
        request.setOutTradeNo(outTradeNo);
        // 调用接口
        return nativePayService.queryOrderByOutTradeNo(request);
    }

    public Transaction payNotify(String wechatPayCertificateSerialNumber, String nonce, String signature, String timestamp, String requestBody) {
        RequestParam requestParam = new RequestParam.Builder()
                .serialNumber(wechatPayCertificateSerialNumber)
                .nonce(nonce)
                .signature(signature)
                .timestamp(timestamp)
                .body(requestBody)
                .build();


        NotificationConfig config = wxPayConfig;

        NotificationParser parser = new NotificationParser(config);

        // 以支付通知回调为例，验签、解密并转换成 Transaction
        Transaction transaction = parser.parse(requestParam, Transaction.class);

        return transaction;
    }
}
