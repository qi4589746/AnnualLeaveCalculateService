package com.mycena.utils.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.mycena.wellpaymainservice.entity.mysql")
@EnableJpaRepositories(basePackages = "com.mycena.utils.entity")
public class MySqlConfig {
}