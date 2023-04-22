package org.scaffold.wx.server.check;

public interface CheckModel {

    public void setToken(String token);

    public String checkSignature();

}
