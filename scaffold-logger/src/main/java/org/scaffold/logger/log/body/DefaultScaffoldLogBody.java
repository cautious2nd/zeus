package org.scaffold.logger.log.body;

public class DefaultScaffoldLogBody {
	private static final String SEPARATOR = "@";
	private String type;
	private String key;
	private String value;
	private String remark;

	public DefaultScaffoldLogBody(String type, String key, String value, String remark) {
		this.type = type;
		this.key = key;
		this.value = value;
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return getType() + SEPARATOR + getKey() + SEPARATOR + getValue() + SEPARATOR + getRemark();
	}

}
