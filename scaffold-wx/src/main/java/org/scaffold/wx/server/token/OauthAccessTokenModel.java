package org.scaffold.wx.server.token;

import org.scaffold.common.http.request.HttpRequestUtils;
import org.scaffold.common.json.GsonUtils;
import org.scaffold.common.spring.SpringContextHolder;
import org.scaffold.logger.log.ScaffoldLogger;
import org.scaffold.wx.config.yml.ScaffoldWxConfig;
import org.scaffold.wx.server.token.response.AccessTokenResponseModel;
import org.scaffold.wx.server.token.response.OauthAccessTokenResponseModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;

public class OauthAccessTokenModel implements AccessTokenModel {

    private static final ScaffoldLogger SCAFFOLD_LOGGER = new ScaffoldLogger(WxAccessTokenModel.class);

    ////https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
    private static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?";


    private String code;

    private String grantType = "authorization_code";//	是	获取access_token填写


    private long createTime;


    private ScaffoldWxConfig wxConfig = SpringContextHolder.getBean(ScaffoldWxConfig.class);


    private OauthAccessTokenModel() {
    }

    public static OauthAccessTokenModel get() {
        return new OauthAccessTokenModel();
    }

    @Override
    public AccessTokenResponseModel getToken() {
        throw new RuntimeException("OauthAccessTokenModel不支持该方法");
    }

    @Override
    public OauthAccessTokenResponseModel getToken(String code) {
        SCAFFOLD_LOGGER.info("开始根据code[{}]获取token信息.",code);
        this.code = code;
        String body = HttpRequestUtils.get(GET_ACCESS_TOKEN_URL + getParams());
        SCAFFOLD_LOGGER.info("返回token信息:{}",body);
        OauthAccessTokenResponseModel oauthAccessTokenResponseModel = GsonUtils.get().readValue(body, OauthAccessTokenResponseModel.class);
       
        return oauthAccessTokenResponseModel;
    }

    private String getParams() {
        return MessageFormat.format("appid={0}&secret={1}&code={2}&grant_type={3}", this.wxConfig.getAppid(), this.wxConfig.getSecret(), this.code, this.grantType);
    }
}
