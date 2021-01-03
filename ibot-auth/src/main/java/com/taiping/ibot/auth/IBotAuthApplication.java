package com.taiping.ibot.auth;

import com.taiping.ibot.common.annotation.EnableIBotLettuceRedis;
import com.taiping.ibot.common.annotation.IBotCloudApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableIBotLettuceRedis
@EnableDiscoveryClient
@SpringBootApplication
@IBotCloudApplication
@MapperScan("com.taiping.ibot.auth.mapper")
public class IBotAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(IBotAuthApplication.class, args);
	}

}
