/**
 * Author : chiziyue
 * Date : 2022年3月30日 上午10:56:56
 * Title : org.scaffold.feign.DefaultScaffoldErrorDecoder.java
 *
**/
package org.scaffold.feign;

import java.io.IOException;
import java.nio.charset.Charset;

import org.scaffold.common.error.AuthorizationException;

import feign.Response;
import feign.Util;

public class DefaultScaffoldErrorDecoder extends ScaffoldErrorDecoder {

	@Override
	public Exception errorHandler(String methodKey, Response response) {
		byte[] body = {};
		try {
			if (response.body() != null) {
				body = Util.toByteArray(response.body().asInputStream());
				if (null != body) {
					String resultStr = new String(body, Charset.forName("UTF-8"));
					logger.warn("feign invoker caused an exception: [{}]", response.status());
					return new AuthorizationException(resultStr);
				}
			}
		} catch (IOException ignored) { // NOPMD
		}

		return new AuthorizationException(response.request().url());
	}

}
