package com.generic.khatabook.product.config;

import com.generic.khatabook.product.exchanger.CustomerClient;
import com.generic.khatabook.product.exchanger.RatingClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import static org.springframework.web.reactive.function.client.WebClient.builder;

@Configuration
public class AppClientConfig {

    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder.build();
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

    @Bean
    public RatingClient reviewClient() {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(reviewWebClient())).build();
        return factory.createClient(RatingClient.class);
    }

    @Bean
    @LoadBalanced
    public WebClient reviewWebClient() {
//        return builder().baseUrl("http://localhost:6300").build();
        return builder().baseUrl("lb://rating-service").build();
    }

}
