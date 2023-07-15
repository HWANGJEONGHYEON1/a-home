package com.ably.demo.common;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginCheckInterceptor()) //LoginCheckInterceptor 등록
				.order(1)
				.addPathPatterns("/**")
				.excludePathPatterns(
						"/user",
						"/error",
						"/login",
						"/logout",
						"/h2-console/*",
						"/v3/api-docs/**",
						"/swagger-resources/**",
						"/swagger-ui.html",
						"/webjars/**",
						"/swagger-ui/**"
				);
	}
  }