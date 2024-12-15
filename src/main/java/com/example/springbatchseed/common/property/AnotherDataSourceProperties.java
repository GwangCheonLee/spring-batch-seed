package com.example.springbatchseed.common.property;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Another 데이터 소스와 관련된 환경 변수를 처리하는 클래스입니다.
 */
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "spring.datasource.another")
public class AnotherDataSourceProperties {

    @NotBlank(message = "ANOTHER_DATABASE_URL must not be blank")
    private String url;

    @NotBlank(message = "ANOTHER_DATABASE_USERNAME must not be blank")
    private String username;

    @NotBlank(message = "ANOTHER_DATABASE_PASSWORD must not be blank")
    private String password;

    @NotBlank(message = "ANOTHER_DATABASE_DRIVER must not be blank")
    private String driverClassName;

    // hbm2ddl-auto: 기본값을 validate로 설정
    private String hbm2ddlAuto = "validate";
}
