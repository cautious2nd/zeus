/**
 * Author : czy
 * Date : 2019年7月4日 上午10:35:42
 * Title : org.scaffold.common.json.GsonUtils.java
 *
**/
package org.scaffold.common.json;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonUtils implements IGsonUtils {

	private static GsonUtils GSON_UTILS = new GsonUtils();;

	private GsonUtils() {
	}

	public static GsonUtils get() {
		return GSON_UTILS;
	}

	@Override
	public String toJson(Object object) {
		Gson gson = new Gson();
		return gson.toJson(object);
	}

	@Override
	public <T> T readValue(String json, Class<T> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(json, clazz);
	}

	@Override
	public <T, V> Map<T, V> readValueToMap(String json, Class<T> key, Class<V> value) {
		Gson gson = new Gson();
		Type type = new TypeToken<Map<T, V>>() {
		}.getType();
		return gson.fromJson(json, type);
	}

	@Override
	public <T> List<T> readValueToList(String json, Class<T> clazz) {
		Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
		Gson gson = new Gson();
		return gson.fromJson(json, typeOfT);
	}

}
