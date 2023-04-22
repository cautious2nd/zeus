package org.scaffold.wx.server.token;

import org.scaffold.wx.server.token.response.AccessTokenResponseModel;

import java.text.MessageFormat;

public interface AccessTokenModel {
    public AccessTokenResponseModel getToken();
}
