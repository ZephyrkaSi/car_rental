package com.gmail.silina.katsiaryna.service.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@ComponentScan
@Configuration
@Testcontainers
/*@EnableJpaRepositories(basePackages = {"com.gmail.silina.katsiaryna.repository"})
@EntityScan("com.gmail.silina.katsiaryna.repository.model")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)*/
public class IntegrationConfig {

    @Bean
    public MySQLContainer mySQLContainer() {
        final var mySQLContainer = new MySQLContainer("mysql:8.0.27")
                .withDatabaseName("CAR_RENTAL");

        mySQLContainer.start();
        return mySQLContainer;
    }

    @Bean
    public DataSource dataSource(final MySQLContainer mySQLContainer) {
        final var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(mySQLContainer.getJdbcUrl());
        hikariConfig.setUsername(mySQLContainer.getUsername());
        hikariConfig.setPassword(mySQLContainer.getPassword());
        return new HikariDataSource(hikariConfig);
    }
}
