package org.scaffold.common.error;

public class ScaffoldFeignException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6989132002309456527L;

	private String statusCode;

	private String message;

	private Object resultData;

	public ScaffoldFeignException(String message) {
		super(message);
		this.message = message;
	}

	public ScaffoldFeignException(String statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
		this.message = message;
	}

	public ScaffoldFeignException(String statusCode, String message, Throwable throwable) {
		super(message, throwable);
		this.statusCode = statusCode;
		this.message = message;
	}

	public ScaffoldFeignException(String statusCode, String message, Object resultData) {
		this.statusCode = statusCode;
		this.message = message;
		this.resultData = resultData;
	}

	public ScaffoldFeignException(String statusCode, String message, Object resultData, Throwable cause) {
		super(cause);
		this.statusCode = statusCode;
		this.message = message;
		this.resultData = resultData;
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

	public Object getResultData() {
		return resultData;
	}

	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}

}
