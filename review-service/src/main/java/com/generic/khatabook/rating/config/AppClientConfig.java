package com.generic.khatabook.rating.config;

import com.generic.khatabook.rating.exchanger.CustomerClient;
import com.generic.khatabook.rating.exchanger.ProductClient;
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
    public ProductClient productClient() {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(specificationWebClient())).build();
        return factory.createClient(ProductClient.class);
    }


    @Bean
    public WebClient customerWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8800").build();
    }

    @Bean
    public WebClient specificationWebClient() {
        return WebClient.builder().baseUrl("http://localhost:6600").build();
    }

}
