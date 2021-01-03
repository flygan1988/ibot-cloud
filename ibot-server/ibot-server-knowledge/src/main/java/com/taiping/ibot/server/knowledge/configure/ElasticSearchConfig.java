package com.taiping.ibot.server.knowledge.configure;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ElasticSearchConfig {
    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String clusterNodes;

    @Bean
    public RestHighLevelClient getEsClient() {
        List<HttpHost> hosts = new ArrayList<>();

        String[] nodes = clusterNodes.split(",");
        for (String node : nodes) {
            hosts.add(HttpHost.create(node));
        }

        RestClientBuilder build = RestClient.builder(hosts.toArray(new HttpHost[0]));
        build.setRequestConfigCallback(builder -> {
            builder.setConnectTimeout(10000);
            builder.setSocketTimeout(60000);
            builder.setConnectionRequestTimeout(5000);
            return builder;
        });

        return new RestHighLevelClient(build);
    }
}
