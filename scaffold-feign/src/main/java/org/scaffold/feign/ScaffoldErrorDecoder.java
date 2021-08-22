/**
 * Author : chiziyue
 * Date : 2021年8月21日 上午12:22:14
 * Title : org.scaffold.feign.ScaffoldErrorDecoder.java
 *
**/
package org.scaffold.feign;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.scaffold.common.http.BaseResult;
import org.scaffold.common.error.BusinessException;
import org.scaffold.common.error.AuthorizationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;

public class ScaffoldErrorDecoder extends ErrorDecoder.Default {

	private static final Logger logger = LoggerFactory.getLogger(ScaffoldErrorDecoder.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public Exception decode(String methodKey, Response response) {
		try {
			if (null != response.body()) {

				BaseResult<?> result = objectMapper.readValue(response.body().asReader(StandardCharsets.UTF_8),
						BaseResult.class);
				logger.warn("feign invoker caused an exception: [{}]", result.getStatusCode());
				int status = response.status();
				if (status >= 500) {
					throw new BusinessException(result.getStatusCode(), result.getMessage(), result.getResultData(),
							null);
				} else if (status == 403) {
					throw new AuthorizationException(result.getStatusCode(), result.getMessage());
				} else if (status >= 400) {
					throw new BusinessException(result.getStatusCode(), result.getMessage(), result.getResultData(),
							null);
				}
			}
		} catch (IOException e) {
		}

		return super.decode(methodKey, response);
	}
}
