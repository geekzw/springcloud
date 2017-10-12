package com.gzw;

import com.gzw.filter.RequestFilter;
import com.gzw.filter.LoginFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringCloudApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RequestFilter ipFilter(){
		return new RequestFilter();
	}

	@Bean
	public LoginFilter loginFilter(){
		return new LoginFilter();
	}
}
