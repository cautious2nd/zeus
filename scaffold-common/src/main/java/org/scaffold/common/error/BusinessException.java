/**
 * Author : chiziyue
 * Date : 2021年8月20日 上午12:18:18
 * Title : org.scaffold.common.error.BusinessException.java
 *
**/
package org.scaffold.common.error;

public class BusinessException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6989132002309456527L;

	private Integer statusCode;

	private String message;

	private Object resultData;

	public BusinessException(Integer statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
		this.message = message;
	}

	public BusinessException(Integer statusCode, String message, Throwable throwable) {
		super(message, throwable);
		this.statusCode = statusCode;
		this.message = message;
	}

	public BusinessException(Integer statusCode, String message, Object resultData) {
		this.statusCode = statusCode;
		this.message = message;
		this.resultData = resultData;
	}

	public BusinessException(Integer statusCode, String message, Object resultData, Throwable cause) {
		super(cause);
		this.statusCode = statusCode;
		this.message = message;
		this.resultData = resultData;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResultData() {
		return resultData;
	}

	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}

}
