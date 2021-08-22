/**
 * Author : chiziyue
 * Date : 2021年8月21日 上午9:55:49
 * Title : org.scaffold.feign.ScaffoldRequestInterceptor.java
 *
**/
package org.scaffold.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class ScaffoldRequestInterceptor implements RequestInterceptor {
	private String token;

	public ScaffoldRequestInterceptor(String token) {
		this.token = token;
	}

	@Override
	public void apply(RequestTemplate template) {

		// TODO JWT生成token
		template.header("scaffold-token", token);
	}
}
