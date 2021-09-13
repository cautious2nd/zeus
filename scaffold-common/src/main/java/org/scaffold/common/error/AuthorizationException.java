/**
 * Author : chiziyue
 * Date : 2021年8月20日 上午12:21:30
 * Title : org.scaffold.common.error.AuthorizationException.java
 *
**/
package org.scaffold.common.error;

public class AuthorizationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6526720812352214390L;

	private String statusCode;

	private String message;

	public AuthorizationException(String statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
		this.message = message;
	}

	/**
	 * @param statusCode 异常码
	 * @param message    异常信息
	 * @param cause      异常原因
	 */
	public AuthorizationException(String statusCode, String message, Throwable cause) {
		super(message, cause);
		this.statusCode = statusCode;
		this.message = message;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
