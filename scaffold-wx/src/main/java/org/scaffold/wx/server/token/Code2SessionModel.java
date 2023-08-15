package org.scaffold.wx.server.token;

import org.scaffold.common.http.request.HttpRequestUtils;
import org.scaffold.common.json.GsonUtils;
import org.scaffold.common.spring.SpringContextHolder;
import org.scaffold.logger.log.ScaffoldLogger;
import org.scaffold.wx.config.yml.ScaffoldWxConfig;
import org.scaffold.wx.server.token.response.Code2SessionResponseModel;
import org.scaffold.wx.server.token.response.OauthAccessTokenResponseModel;

import java.text.MessageFormat;

public class Code2SessionModel {
    private static final ScaffoldLogger SCAFFOLD_LOGGER = new ScaffoldLogger(Code2SessionModel.class);

    private static final String GET_SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?";

    private String jsCode;//   js_code	string	是	登录时获取的 code，可通过wx.login获取
    private String grantType="authorization_code";//  grant_type	string	是	授权类型，此处只需填写 authorization_code

    private ScaffoldWxConfig wxConfig = SpringContextHolder.getBean(ScaffoldWxConfig.class);

    public String getJsCode() {
        return jsCode;
    }

    public void setJsCode(String jsCode) {
        this.jsCode = jsCode;
    }

    public String getGrantType() {
        return grantType;
    }


    public Code2SessionResponseModel getSession(String jsCode){
        SCAFFOLD_LOGGER.info("开始根据code[{}]获取token信息.",jsCode);
        this.jsCode = jsCode;
        String body = HttpRequestUtils.get(GET_SESSION_URL + getParams());
        SCAFFOLD_LOGGER.info("返回token信息:{}",body);
        Code2SessionResponseModel code2SessionResponseModel = GsonUtils.get().readValue(body, Code2SessionResponseModel.class);

        return code2SessionResponseModel;
    }


    private String getParams() {
        return MessageFormat.format("appid={0}&secret={1}&js_code={2}&grant_type={3}", this.wxConfig.getAppid(), this.wxConfig.getSecret(), this.jsCode, this.grantType);
    }
}
