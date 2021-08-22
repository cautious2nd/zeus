/**
 * Author : chiziyue
 * Date : 2021年8月20日 上午12:23:42
 * Title : org.scaffold.common.http.BaseResult.java
 *
**/
package org.scaffold.common.http;

import java.io.Serializable;

public class BaseResult<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7572227305644025365L;

	private Integer statusCode;

	private String message;

	private T resultData;

	public BaseResult() {
	}

	public BaseResult(Integer statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	public BaseResult(Integer statusCode, String message, T resultData) {
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

	public T getResultData() {
		return resultData;
	}

	public void setResultData(T resultData) {
		this.resultData = resultData;
	}
	
	
}
