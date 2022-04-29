/**
 * Author : chiziyue
 * Date : 2022年4月29日 下午3:10:19
 * Title : org.scaffold.common.security.auth.BusinessAuthenticationEntity.java
 *
**/
package org.scaffold.common.security.auth;

import java.util.List;

public class BusinessAuthenticationEntity {
	private String originData;
	private String managerNo;
	private String managerName;
	private List<String> roles;
	private List<String> depts;

	public String getOriginData() {
		return originData;
	}

	public void setOriginData(String originData) {
		this.originData = originData;
	}

	public String getManagerNo() {
		return managerNo;
	}

	public void setManagerNo(String managerNo) {
		this.managerNo = managerNo;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<String> getDepts() {
		return depts;
	}

	public void setDepts(List<String> depts) {
		this.depts = depts;
	}

}
