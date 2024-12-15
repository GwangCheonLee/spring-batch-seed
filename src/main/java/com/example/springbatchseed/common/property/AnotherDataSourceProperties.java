package com.example.springbatchseed.common.property;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Another 데이터 소스의 환경 변수를 매핑하고 검증하는 클래스입니다.
 * <p>
 * {@code spring.datasource.another}로 시작하는 설정을 매핑하며, H2 데이터베이스가 아닌 경우 {@code password} 값이 필수입니다. 검증
 * 실패 시 애플리케이션이 종료됩니다.
 * </p>
 */
@Slf4j
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "spring.datasource.another")
public class AnotherDataSourceProperties {

    @NotBlank(message = "ANOTHER_DATABASE_URL must not be blank")
    private String url;

    @NotBlank(message = "ANOTHER_DATABASE_USERNAME must not be blank")
    private String username;

    private String password;

    @NotBlank(message = "ANOTHER_DATABASE_DRIVER must not be blank")
    private String driverClassName;

    private String hbm2ddlAuto = "validate";

    /**
     * 데이터 소스 설정 값을 검증합니다.
     * <p>
     * H2 데이터베이스가 아닌 경우 {@code password}가 필수입니다. 조건을 만족하지 않으면 {@code IllegalStateException}을
     * 발생시킵니다.
     * </p>
     */
    @PostConstruct
    private void validateProperties() {
        log.info("Starting validation of AnotherDataSourceProperties...");

        boolean isH2Database = "org.h2.Driver".equals(driverClassName);

        log.info("Database Driver: {}", driverClassName);
        log.info("Active Profile: {}", System.getProperty("spring.profiles.active", "default"));

        if (!isH2Database && (password == null || password.isEmpty())) {
            log.error("Validation failed: ANOTHER_DATABASE_PASSWORD is missing.");
            log.error(
                "Application will shut down. Ensure the following properties are correctly set:");
            log.error("- spring.datasource.another.password");
            log.error("- spring.datasource.another.driver-class-name");

            throw new IllegalStateException(
                String.format(
                    "Validation failed: ANOTHER_DATABASE_PASSWORD is required but not provided for driver: %s",
                    driverClassName));
        }

        log.info("AnotherDataSourceProperties validation completed successfully.");
    }
}
