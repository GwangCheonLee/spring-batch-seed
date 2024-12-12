package com.example.springbatchseed.common.property;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Spring Batch와 관련된 환경 변수를 처리하는 클래스입니다.
 */
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "spring.batch")
public class BatchProperties {

    /**
     * Chunk 크기 설정 (기본값: 100)
     */
    @Min(value = 1, message = "CHUNK_SIZE must be at least 1")
    private int chunkSize = 100;
}
