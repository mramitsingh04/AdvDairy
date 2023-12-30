package com.generic.khatabook.config;

import com.generic.khatabook.exchanger.CustomerSpecificationClient;
import com.generic.khatabook.exchanger.ProductClient;
import com.generic.khatabook.exchanger.SpecificationClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class AppClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl("http://localhost:6600").build();
    }

    @Bean
    public SpecificationClient specificationClient() {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient())).build();
        return factory.createClient(SpecificationClient.class);
    }

    @Bean
    public ProductClient productClient() {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient())).build();
        return factory.createClient(ProductClient.class);
    }


    @Bean
    public CustomerSpecificationClient customerSpecificationClient() {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient())).build();
        return factory.createClient(CustomerSpecificationClient.class);
    }
}