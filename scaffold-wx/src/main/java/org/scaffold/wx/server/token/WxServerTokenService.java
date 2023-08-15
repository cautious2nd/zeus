package org.scaffold.wx.server.token;


import org.scaffold.wx.server.token.response.AccessTokenResponseModel;
import org.scaffold.wx.server.token.response.Code2SessionResponseModel;
import org.scaffold.wx.server.token.response.OauthAccessTokenResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WxServerTokenService {
    @Autowired
    @Qualifier("wxAccessTokenModel")
    private AccessTokenModel accessTokenModel;


    /**
     * 获取AccessToken
     *
     * @return
     */
    public AccessTokenResponseModel getAccessToken() {
        return accessTokenModel.getToken();
    }


    public OauthAccessTokenResponseModel getOauthAccessToken(String code) {
        return OauthAccessTokenModel.get().getToken(code);
    }


    /**
     * 小程序登录
     */
    public Code2SessionResponseModel code2Session(String jsCode) {
        Code2SessionModel model = new Code2SessionModel();
        return  model.getSession(jsCode);
    }

}
