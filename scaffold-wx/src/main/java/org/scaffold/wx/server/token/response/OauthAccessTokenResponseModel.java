package org.scaffold.wx.server.token.response;

public class OauthAccessTokenResponseModel extends AccessTokenResponseModel{

    private String refresh_token;
    private String openid;
    private String scope;
    private String is_snapshotuser;
    private String unionid;


    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getIs_snapshotuser() {
        return is_snapshotuser;
    }

    public void setIs_snapshotuser(String is_snapshotuser) {
        this.is_snapshotuser = is_snapshotuser;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
