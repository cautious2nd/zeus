package org.scaffold.wx.server.token;

import org.scaffold.common.http.request.HttpRequestUtils;
import org.scaffold.common.json.GsonUtils;
import org.scaffold.wx.server.token.response.AccessTokenResponseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Date;

@Component
public class WxAccessTokenModel implements AccessTokenModel {


    private String access_token;
    private long expires_in;

    private long createTime;


    private String grantType = "client_credential";//	是	获取access_token填写
    @Value("${wx.server.appid}")
    private String appid;
    @Value("${wx.server.appsecret}")
    private String secret;
    @Value("${wx.server.accessTokenUrl}")
    private String getAccessTokenUrl;


    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    private String getParams() {
        return MessageFormat.format("grant_type={0}&appid={1}&secret={2}", this.grantType, this.appid, this.secret);
    }


    public AccessTokenResponseModel getToken() {

        //时间判断，看看是否快过期了
        if ((System.currentTimeMillis() - createTime) / 1000 > (expires_in - 5 * 60)) {
            //获取新token
            String body = HttpRequestUtils.get(getAccessTokenUrl + "?" + getParams());
            AccessTokenResponseModel accessTokenResponseModel = GsonUtils.get().readValue(body, AccessTokenResponseModel.class);
            createTime = System.currentTimeMillis();
            return accessTokenResponseModel;
        } else {
            //返回旧的token
            AccessTokenResponseModel accessTokenResponseModel = new AccessTokenResponseModel();
            accessTokenResponseModel.setAccess_token(access_token);
            accessTokenResponseModel.setExpires_in(expires_in);
            return accessTokenResponseModel;
        }


    }
}
