/**
 * Author : chiziyue
 * Date : 2022年4月29日 下午3:10:19
 * Title : org.scaffold.common.security.auth.BusinessAuthenticationEntity.java
 *
**/
package org.scaffold.common.security.auth;

public class BusinessAuthenticationEntity {
	private String originData;
	private String authData;

	public String getOriginData() {
		return originData;
	}

	public void setOriginData(String originData) {
		this.originData = originData;
	}

	public String getAuthData() {
		return authData;
	}

	public void setAuthData(String authData) {
		this.authData = authData;
	}

}
