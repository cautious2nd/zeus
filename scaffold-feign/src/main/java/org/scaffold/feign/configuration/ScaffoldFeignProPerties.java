/**
 * Author : chiziyue
 * Date : 2021年8月21日 上午9:49:54
 * Title : org.scaffold.feign.configuration.ScaffoldFeignProPerties.java
 *
**/
package org.scaffold.feign.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "scaffold.feign")
public class ScaffoldFeignProPerties {
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
