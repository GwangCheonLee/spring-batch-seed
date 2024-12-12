package com.example.springbatchseed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringBatchSeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchSeedApplication.class, args);
    }

}
