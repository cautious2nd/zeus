/**
 * Author : chiziyue
 * Date : 2021年8月21日 上午12:22:14
 * Title : org.scaffold.feign.ScaffoldErrorDecoder.java
 *
**/
package org.scaffold.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ScaffoldErrorDecoder extends ErrorDecoder.Default {

	protected static final Logger logger = LoggerFactory.getLogger(ScaffoldErrorDecoder.class);

	public abstract Exception errorHandler(String methodKey, Response response);

	@Override
	public Exception decode(String methodKey, Response response) {

//		Exception e = super.decode(methodKey, response);

		return errorHandler(methodKey, response);
	}
}
