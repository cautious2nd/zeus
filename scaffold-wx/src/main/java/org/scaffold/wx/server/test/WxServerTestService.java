package org.scaffold.wx.server.test;

import org.scaffold.common.http.request.HttpRequestUtils;
import org.scaffold.wx.server.token.AccessTokenModel;
import org.scaffold.wx.server.token.OauthAccessTokenModel;
import org.scaffold.wx.server.token.response.OauthAccessTokenResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.MessageFormat;

@Service
public class WxServerTestService {


    @Autowired
    @Qualifier("wxAccessTokenModel")
    private AccessTokenModel accessTokenModel;



    /**
     * 获取微信服务IP
     *
     * @return
     */
    public String getWxServerIps() {

//        https://api.weixin.qq.com/cgi-bin/get_api_domain_ip?access_token=ACCESS_TOKEN

        String body = HttpRequestUtils.get("https://api.weixin.qq.com/cgi-bin/get_api_domain_ip?access_token=" + accessTokenModel.getToken().getAccess_token());


        return body;
    }

    public String getWxUserList() {
        //https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID


        String body = HttpRequestUtils.get("https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + accessTokenModel.getToken().getAccess_token());

        return body;
    }

    public String getUserInfo(String code) {
        //https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN

        OauthAccessTokenResponseModel model = OauthAccessTokenModel.get().getToken(code);

        String url = MessageFormat.format("https://api.weixin.qq.com/sns/userinfo?access_token={}&openid={}&lang={}", model.getAccess_token(), model.getOpenid(), "zh_CN");
        String body = HttpRequestUtils.get(url);
        return body;
    }

}
