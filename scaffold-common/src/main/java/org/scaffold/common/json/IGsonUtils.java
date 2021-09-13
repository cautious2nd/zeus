/**
 * Author : chiziyue
 * Date : 2021年8月24日 下午10:09:40
 * Title : org.scaffold.common.json.IGsonUtils.java
 *
**/
package org.scaffold.common.json;

import java.util.List;

public interface IGsonUtils {
	public String toJson(Object object);

	public <T> T readValue(String json, Class<T> clazz);

	public <T> List<T> readValueToList(String json, Class<T> clazz);
}
