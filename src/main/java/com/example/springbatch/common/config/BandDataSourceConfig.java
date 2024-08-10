package com.example.springbatch.common.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.springbatch.band",
        entityManagerFactoryRef = "bandEntityManagerFactory",
        transactionManagerRef = "bandTransactionManager"
)
public class BandDataSourceConfig {

    @Value("${spring.datasource.band.url}")
    private String bandDbUrl;

    @Value("${spring.datasource.band.username}")
    private String bandDbUsername;

    @Value("${spring.datasource.band.password}")
    private String bandDbPassword;

    @Value("${spring.datasource.band.driver-class-name}")
    private String bandDbDriverClassName;

    @Bean(name = "bandDataSource")
    public DataSource bandDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(bandDbDriverClassName)
                .url(bandDbUrl)
                .username(bandDbUsername)
                .password(bandDbPassword)
                .build();
    }

    @Bean(name = "bandEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean bandEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("bandDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.example.springbatch.band.entities")
                .persistenceUnit("band")
                .build();
    }

    @Bean(name = "bandTransactionManager")
    public PlatformTransactionManager bandTransactionManager(@Qualifier("bandEntityManagerFactory") EntityManagerFactory bandEntityManagerFactory) {
        return new JpaTransactionManager(bandEntityManagerFactory);
    }
}
