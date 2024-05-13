package com.generic.khatabook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.generic.khatabook.*", "com.generic.khatabook.config"})
@EnableJpaRepositories("com.generic.khatabook.repository")
@EnableDiscoveryClient

public class GenericKhatabookApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(GenericKhatabookApplication.class);
//        application.setBannerMode(Banner.Mode.CONSOLE);
        application.run(args);
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("messages");
        return resourceBundleMessageSource;
    }
}
