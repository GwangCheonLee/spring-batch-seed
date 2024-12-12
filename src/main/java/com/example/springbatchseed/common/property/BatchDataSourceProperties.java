package com.example.springbatchseed.common.property;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Batch 데이터 소스와 관련된 환경 변수를 처리하는 클래스입니다.
 */
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "spring.datasource.batch")
public class BatchDataSourceProperties {

    @NotBlank(message = "BATCH_DATABASE_URL must not be blank")
    private String url;

    @NotBlank(message = "BATCH_DATABASE_USERNAME must not be blank")
    private String username;

    @NotBlank(message = "BATCH_DATABASE_PASSWORD must not be blank")
    private String password;

    @NotBlank(message = "BATCH_DATABASE_DRIVER must not be blank")
    private String driverClassName;
}
