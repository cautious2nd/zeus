package org.scaffold.wx.server.token;

import org.scaffold.common.http.request.HttpRequestUtils;
import org.scaffold.common.json.GsonUtils;
import org.scaffold.logger.log.ScaffoldLogger;
import org.scaffold.wx.config.yml.ScaffoldWxConfig;
import org.scaffold.wx.server.token.response.AccessTokenResponseModel;
import org.scaffold.wx.server.token.response.OauthAccessTokenResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Date;

@Component
public class WxAccessTokenModel implements AccessTokenModel {

    private static final ScaffoldLogger SCAFFOLD_LOGGER = new ScaffoldLogger(WxAccessTokenModel.class);

    private static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?";

    @Autowired
    private ScaffoldWxConfig wxConfig;

    private String access_token;
    private long expires_in;

    private long createTime;


    private String grantType = "client_credential";//	是	获取access_token填写


    private String getParams() {
        return MessageFormat.format("grant_type={0}&appid={1}&secret={2}", this.grantType, this.wxConfig.getAppid(), this.wxConfig.getSecret());
    }

    public AccessTokenResponseModel getToken() {
        AccessTokenResponseModel accessTokenResponseModel = null;
        try {
            //时间判断，看看是否快过期了
            if ((System.currentTimeMillis() - createTime) / 1000 > (expires_in - 5 * 60)) {
                //获取新token

                synchronized (this) {
                    String body = HttpRequestUtils.get(GET_ACCESS_TOKEN_URL + getParams());
                    accessTokenResponseModel = GsonUtils.get().readValue(body, AccessTokenResponseModel.class);
                    createTime = System.currentTimeMillis();
                    expires_in = accessTokenResponseModel.getExpires_in();
                    access_token = accessTokenResponseModel.getAccess_token();
                    return accessTokenResponseModel;
                }

            } else {
                //返回旧的token
                accessTokenResponseModel = new AccessTokenResponseModel();
                accessTokenResponseModel.setAccess_token(access_token);
                accessTokenResponseModel.setExpires_in(expires_in);
                return accessTokenResponseModel;
            }
        } finally {
            SCAFFOLD_LOGGER.info("获取新的accessToken,返回值:{}", GsonUtils.get().toJson(accessTokenResponseModel));
        }


    }

    @Override
    public OauthAccessTokenResponseModel getToken(String code) {
        throw new RuntimeException("WxAccessTokenModel不支持该方法");
    }
}
