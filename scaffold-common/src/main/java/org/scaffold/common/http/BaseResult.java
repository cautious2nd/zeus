/**
 * Author : chiziyue
 * Date : 2021年8月20日 上午12:23:42
 * Title : org.scaffold.common.http.BaseResult.java
 *
**/
package org.scaffold.common.http;

import java.io.Serializable;

import org.scaffold.common.http.result.ResultStatus;

public class BaseResult<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7572227305644025365L;

	private String statusCode;

	private String message;

	private T resultData;

	public BaseResult() {
	}

	public BaseResult(String statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	public BaseResult(String statusCode, String message, T resultData) {
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

	public T getResultData() {
		return resultData;
	}

	public void setResultData(T resultData) {
		this.resultData = resultData;
	}

	public static <T> BaseResult<T> success() {
		return success(null);
	}

	public static <T> BaseResult<T> success(T data) {
		ResultStatus rce = ResultStatus.SUCCESS;
		if (data instanceof Boolean && Boolean.FALSE.equals(data)) {
			rce = ResultStatus.SYSTEM_EXECUTION_ERROR;
		}
		return result(rce, data);
	}

	public static <T> BaseResult<T> failed(ResultStatus resultStatus) {
		return result(resultStatus.getStatusCode(), resultStatus.getMessage(), null);
	}

	public static <T> BaseResult<T> failed(ResultStatus resultStatus, String msg) {
		return result(resultStatus.getStatusCode(), msg, null);
	}

	private static <T> BaseResult<T> result(ResultStatus resultStatus, T data) {
		return result(resultStatus.getStatusCode(), resultStatus.getMessage(), data);
	}

	private static <T> BaseResult<T> result(String statusCode, String message, T resultData) {
		BaseResult<T> result = new BaseResult<>();
		result.setStatusCode(statusCode);
		result.setResultData(resultData);
		result.setMessage(message);
		return result;
	}
}
