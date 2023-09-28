package com.generic.khatabook.rating.config;

import com.generic.khatabook.rating.exchanger.CustomerClient;
import com.generic.khatabook.rating.exchanger.ProductClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class AppClientConfig {

    @Bean
    public CustomerClient customerClient() {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(customerWebClient())).build();
        return factory.createClient(CustomerClient.class);
    }
    @Bean
    @LoadBalanced
    public ProductClient productClient() {

        final WebClientAdapter clientAdapter = WebClientAdapter.forClient(productServiceWebClient());
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(clientAdapter).build();
        return factory.createClient(ProductClient.class);
    }
    @Bean
    @LoadBalanced
    WebClient.Builder builder() {
        return WebClient.builder();
    }

    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }

    @Bean
    public WebClient customerWebClient() {
        return builder().baseUrl("http://localhost:8800").build();
    }

    @Bean
    @LoadBalanced
    public WebClient productServiceWebClient() {
        return builder().baseUrl("lb://product-service").build();
    }

}
