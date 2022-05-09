/**
 * Author : chiziyue
 * Date : 2022年4月27日 下午5:10:27
 * Title : org.scaffold.common.resource.ResourceUtils.java
 *
**/
package org.scaffold.common.resource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ResourceUtils {
	protected static final Logger LOGGER = LoggerFactory.getLogger(ResourceUtils.class);

	public static void resourceJsonReadConsumer(String resourceName, Consumer<String> consumer) {

		try {
			Resource fileRource = new ClassPathResource(resourceName);

			InputStream input = fileRource.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(input);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			StringBuilder buffer = new StringBuilder();

			try {
				bufferedReader.lines().forEach(s -> {
					buffer.append(s);
				});

				String json = buffer.toString();
				consumer.accept(json);

			} finally {
				if (bufferedReader != null)
					bufferedReader.close();
				if (inputStreamReader != null)
					inputStreamReader.close();
				if (input != null)
					input.close();
			}

		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage());
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}

	}
}
