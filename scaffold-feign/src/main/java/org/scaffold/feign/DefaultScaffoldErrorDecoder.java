/**
 * Author : chiziyue
 * Date : 2022年3月30日 上午10:56:56
 * Title : org.scaffold.feign.DefaultScaffoldErrorDecoder.java
 *
**/
package org.scaffold.feign;

import feign.Response;
import feign.Util;
import org.scaffold.common.error.ScaffoldFeignException;

import java.io.IOException;
import java.nio.charset.Charset;

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
					return new ScaffoldFeignException(String.valueOf(response.status()), resultStr, methodKey);
				}
			}
		} catch (IOException ignored) { // NOPMD
		}

		return new ScaffoldFeignException(String.valueOf(response.status()), response.request().url(), methodKey);
	}

}
