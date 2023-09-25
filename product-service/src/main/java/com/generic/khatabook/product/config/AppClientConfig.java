package com.generic.khatabook.product.config;

import com.generic.khatabook.product.exchanger.CustomerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class AppClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl("http://localhost:8800").build();
    }

    @Bean
    public CustomerClient customerClient() {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient())).build();
        return factory.createClient(CustomerClient.class);
    }

}
