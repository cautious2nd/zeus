/**
 * Author : chiziyue
 * Date : 2021年8月24日 下午4:05:16
 * Title : org.scaffold.common.http.BasePage.java
 *
**/
package org.scaffold.common.http;

import java.io.Serializable;
import java.util.List;

public class BasePage<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8574109281523664108L;
	private Integer totalRow;
	private Integer pageCurrent;
	private List<T> list;

	public BasePage() {
	}

	public BasePage(int totalRow, int pageCurrent, List<T> list) {
		this.totalRow = totalRow;
		this.pageCurrent = pageCurrent;
		this.list = list;
	}

	public Integer getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(Integer totalRow) {
		this.totalRow = totalRow;
	}

	public Integer getPageCurrent() {
		return pageCurrent;
	}

	public void setPageCurrent(Integer pageCurrent) {
		this.pageCurrent = pageCurrent;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
