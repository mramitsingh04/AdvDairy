package com.generic.khatabook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

//@Configuration
public class DBConnect {
    private static String PROP_DB_DRIVER_CLASS = "spring.datasource.driverClassName";
    private static String PROP_DB_URL = "spring.datasource.url";
    private static String PROP_DB_USER = "spring.datasource.username";
    private static String PROP_DB_PASS = "spring.datasource.password";

    @Autowired
    private Environment env;

    @Bean("dataSource")
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create().driverClassName(env.getProperty(PROP_DB_DRIVER_CLASS)).url(env.getProperty(PROP_DB_URL)).username(env.getProperty(PROP_DB_USER)).password(env.getProperty(PROP_DB_PASS)).build();
    }

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.generic.khatabook.entity");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaPropertyMap(additionalProperties());

        return em;
    }

    private Map<String, Object> additionalProperties() {
        return null;
    }
}
