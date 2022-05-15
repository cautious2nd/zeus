/**
 * Author : chiziyue
 * Date : 2022年5月15日 下午8:48:46
 * Title : org.scaffold.mongo.service.page.Page.java
 *
**/
package org.scaffold.mongo.service.page;

import org.scaffold.common.json.annotation.IgnorRead;

public class Page {

	@IgnorRead
	private int totalRow;// 总条数

	@IgnorRead
	private int pageCurrent = 1; // 当前页

	@IgnorRead
//	private int pageSize = Integer.valueOf(Global.getConfig("page.pageSize")); // 页面大小，设置为“-1”表示不进行分页（分页无效）
	private int pageSize = 20;

	@IgnorRead
	private String dbName;

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	public int getPageCurrent() {
		return pageCurrent;
	}

	public void setPageCurrent(int pageCurrent) {
		this.pageCurrent = pageCurrent;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * 获取 Hibernate FirstResult
	 */
	
	
	public int getFirstResult() {
		int firstResult = (getPageCurrent() - 1) * getPageSize();
		if (firstResult >= getTotalRow()) {
			firstResult = 0;
		}
		return firstResult;
	}

	/**
	 * 获取 Hibernate MaxResults
	 */
	
	public int getMaxResults() {
		return getPageSize();
	}

}
