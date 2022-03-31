/**
 * Author : chiziyue
 * Date : 2022年3月30日 上午10:56:56
 * Title : org.scaffold.feign.DefaultScaffoldErrorDecoder.java
 *
**/
package org.scaffold.feign;

import java.io.IOException;

import org.scaffold.common.error.AuthorizationException;
import org.scaffold.common.error.BusinessException;

import feign.Response;
import feign.Util;

public class DefaultScaffoldErrorDecoder extends ScaffoldErrorDecoder {

	@Override
	public Exception errorHandler(String methodKey, Response response) throws RuntimeException {
		try {
			if (null != response.body()) {

				String resultStr = Util.toString(response.body().asReader(Util.UTF_8));

				logger.warn("feign invoker caused an exception: [{}]", response.status());

				int status = response.status();
				if (status >= 500) {
					throw new BusinessException(String.valueOf(status), resultStr);
				} else if (status == 403) {
					throw new AuthorizationException(resultStr);
				} else if (status >= 400) {
					throw new BusinessException(String.valueOf(status), resultStr);
				}
			}
		} catch (IOException e) {
		}
		return super.decode(methodKey, response);
	}

}
