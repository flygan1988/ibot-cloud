package com.taiping.ibot.server.knowledge.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:ibot-server-knowledge.properties"})
@ConfigurationProperties(prefix = "ibot.server.knowledge")
public class IBotServerKnowledgeProperties {

    private String anonUrl;
    private IBotSwaggerProperties swagger = new IBotSwaggerProperties();
}
