package com.example.elasticsearch.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
@Data
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
public class ESConfig extends AbstractElasticsearchConfiguration {
    private String host;
    private Integer port;
    @Override
    //要么继承，要么@bean
    //@Bean
    public RestHighLevelClient elasticsearchClient() {
        RestClientBuilder builder=RestClient.builder(new HttpHost(host,port));
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(builder);
        return  restHighLevelClient;

    }
}
