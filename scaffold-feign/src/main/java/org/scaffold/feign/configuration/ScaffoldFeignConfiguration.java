/**
 * Author : chiziyue
 * Date : 2021年8月21日 上午9:45:16
 * Title : org.scaffold.feign.configuration.ScaffoldFeignConfiguration.java
 *
**/
package org.scaffold.feign.configuration;

import java.util.stream.Collectors;

import org.scaffold.feign.DefaultScaffoldErrorDecoder;
import org.scaffold.feign.ScaffoldErrorDecoder;
import org.scaffold.feign.ScaffoldRequestInterceptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import feign.RequestInterceptor;

@Configuration
@EnableConfigurationProperties(ScaffoldFeignProPerties.class)
public class ScaffoldFeignConfiguration {
	@Autowired
	ScaffoldFeignProPerties scaffoldFeignProPerties;

	@Bean
	@ConditionalOnMissingBean
	public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converter) {
		return new HttpMessageConverters(converter.orderedStream().collect(Collectors.toList()));
	}

	@Bean
	@ConditionalOnMissingBean(value = ScaffoldErrorDecoder.class)
	public ScaffoldErrorDecoder errorDecoder() {
		return new DefaultScaffoldErrorDecoder();
	}

	@Bean
	@ConditionalOnProperty(value = "scaffold.rest.enableTokenApply", havingValue = "true", matchIfMissing = true)
	public RequestInterceptor requestInterceptor() {
		return new ScaffoldRequestInterceptor(scaffoldFeignProPerties.getToken());
	}
}
