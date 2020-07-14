package com.bookstore.bookstore.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


/**
 * @author shivani_reddy
 */

@Configuration
public class AppConfig {

    @Value("${http.connection.pool.size:100}")
    private String poolMaxTotal;

    @Value("${http.connection.timeout:5000}")
    private String connectionTimeOut;

    @Value("${http.read.timeout:10000}")
    private String readTimeOut;

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
        return getRestTemplate(Integer.parseInt(connectionTimeOut), Integer.parseInt(readTimeOut),
                Integer.parseInt(poolMaxTotal));
    }

    public HttpClient httpClient(int noOfConnections) {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(noOfConnections);
        return HttpClientBuilder.create().setConnectionManager(connectionManager).build();
    }

    public ClientHttpRequestFactory httpRequestFactory(int connectionTimeout, int readTimeout,
                                                       int maxConnections) {
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient(maxConnections));
        factory.setConnectTimeout(connectionTimeout);
        factory.setReadTimeout(readTimeout);
        return new BufferingClientHttpRequestFactory(factory);
    }

    public RestTemplate getRestTemplate(int connectionTimeout, int readTimeout,
                                        int maxConnections) {

        RestTemplate restTemplate = new RestTemplate(httpRequestFactory(connectionTimeout, readTimeout,
                maxConnections));
        return restTemplate;
    }
}
