package com.generic.khatabook.notebook.config;

import com.generic.khatabook.notebook.exchanger.CustomerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class AppClientConfig {


    @Bean
    @LoadBalanced
    WebClient.Builder builder() {
        return WebClient.builder();
    }

    @Bean
    public CustomerClient customerClient() {
        final WebClientAdapter clientAdapter = WebClientAdapter.forClient(customerWebClient());
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(clientAdapter).build();
        return factory.createClient(CustomerClient.class);
    }

    @Bean
    @LoadBalanced
    public WebClient customerWebClient() {
        return builder().baseUrl("lb://khatabook-service").build();
    }

}