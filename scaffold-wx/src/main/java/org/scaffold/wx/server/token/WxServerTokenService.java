package org.scaffold.wx.server.token;


import org.scaffold.wx.server.token.response.AccessTokenResponseModel;
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
     * @return
     */
    public AccessTokenResponseModel getAccessToken() {
        return accessTokenModel.getToken();
    }


}
