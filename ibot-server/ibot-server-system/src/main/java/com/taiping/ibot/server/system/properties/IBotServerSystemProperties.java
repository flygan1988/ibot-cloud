package com.taiping.ibot.server.system.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:ibot-server-system.properties"})
@ConfigurationProperties(prefix = "ibot.server.system")
public class IBotServerSystemProperties {
    private String anonUrl;
    private IBotSwaggerProperties swagger = new IBotSwaggerProperties();
}
