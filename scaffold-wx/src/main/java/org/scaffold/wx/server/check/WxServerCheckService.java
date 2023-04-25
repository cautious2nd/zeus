package org.scaffold.wx.server.check;

import org.scaffold.wx.config.yml.ScaffoldWxConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class WxServerCheckService {

    @Autowired
    private ScaffoldWxConfig wxConfig;

    public String checkSignature(CheckModel checkModel) {
//    signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
//    timestamp	时间戳
//    nonce	随机数
//    echostr	随机字符串
        checkModel.setToken(wxConfig.getToken());

        return checkModel.checkSignature();

    }
}
