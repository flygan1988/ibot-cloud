package com.taiping.ibot.server.knowledge;

import com.taiping.ibot.common.annotation.IBotCloudApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
@EnableGlobalMethodSecurity(prePostEnabled = true)
@IBotCloudApplication
@MapperScan("com.taiping.ibot.server.knowledge.mapper")
public class IBotServerKnoweldgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(IBotServerKnoweldgeApplication.class, args);
	}

}
