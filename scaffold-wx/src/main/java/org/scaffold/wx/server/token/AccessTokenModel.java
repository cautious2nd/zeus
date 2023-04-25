package org.scaffold.wx.server.token;

import org.scaffold.wx.server.token.response.AccessTokenResponseModel;
import org.scaffold.wx.server.token.response.OauthAccessTokenResponseModel;

import java.text.MessageFormat;

public interface AccessTokenModel {
    public AccessTokenResponseModel getToken();

    public OauthAccessTokenResponseModel getToken(String code);
}
