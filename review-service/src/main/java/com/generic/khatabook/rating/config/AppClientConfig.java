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
        final WebClientAdapter clientAdapter = WebClientAdapter.forClient(customerWebClient());
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(clientAdapter).build();
        return factory.createClient(CustomerClient.class);
    }

    @Bean
    @LoadBalanced
    public WebClient customerWebClient() {
//        return builder().baseUrl("http://localhost:8600/").build();
        return builder().baseUrl("lb://khatabook-service").build();
    }

    @Bean
    @LoadBalanced
    WebClient.Builder builder() {
        return WebClient.builder();
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
    public WebClient productServiceWebClient() {
        return builder().baseUrl("lb://product-service").build();
    }

    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }

}
