/**
 * Author : chiziyue
 * Date : 2021年11月12日 下午4:48:53
 * Title : org.scaffold.common.security.jwt.BaseJWT.java
 *
**/
package org.scaffold.common.security.jwt;

public class BaseJWT {
	private String accessToken;
	private String refreshToken;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
