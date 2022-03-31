/**
 * Author : chiziyue
 * Date : 2021年8月21日 上午12:22:14
 * Title : org.scaffold.feign.ScaffoldErrorDecoder.java
 *
**/
package org.scaffold.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import feign.Response;
import feign.codec.ErrorDecoder;

public abstract class ScaffoldErrorDecoder extends ErrorDecoder.Default {

	protected static final Logger logger = LoggerFactory.getLogger(ScaffoldErrorDecoder.class);

	public abstract Exception errorHandler(String methodKey, Response response) throws RuntimeException;

	@Override
	public Exception decode(String methodKey, Response response) {

		return errorHandler(methodKey, response);
	}
}
