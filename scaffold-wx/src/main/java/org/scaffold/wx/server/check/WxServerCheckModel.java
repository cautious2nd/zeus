package org.scaffold.wx.server.check;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WxServerCheckModel implements CheckModel {

    private String token;//
    private String signature;//微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
    private String timestamp;//timestamp	时间戳
    private String nonce;//  nonce	随机数
    private String echostr;//  echostr	随机字符串


    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String checkSignature() {
        String[] checkArray = {this.token, this.timestamp, this.nonce};

        List<String> paramList = Arrays.stream(checkArray).sorted().collect(Collectors.toList());


        String checkParam = String.join("", paramList);


        checkParam = DigestUtils.sha1Hex(checkParam);

        if (checkParam.equals(this.signature)) {
            return this.echostr;
        } else {
            return "nonono";
        }
    }


    public void setSignature(String signature) {
        this.signature = signature;
    }



    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }



    public void setNonce(String nonce) {
        this.nonce = nonce;
    }



    public void setEchostr(String echostr) {
        this.echostr = echostr;
    }
}
