package com.example.springbatchseed.common.property;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Slf4j
@SpringJUnitConfig
@TestPropertySource(properties = {"spring.batch.chunk-size=200"})
class BatchPropertiesTest {

    @Autowired
    private BatchProperties batchProperties;

    @Test
    void testChunkSizeProperty() {
        // 주입된 chunkSize 값이 200인지 확인
        assertThat(batchProperties.getChunkSize()).isEqualTo(200);
    }

    @Test
    void testDefaultChunkSize() {
        // 기본값이 100으로 설정되는지 확인
        BatchProperties defaultProperties = new BatchProperties();
        assertThat(defaultProperties.getChunkSize()).isEqualTo(100);
    }

    @Configuration
    @EnableConfigurationProperties(BatchProperties.class)
    static class TestConfig {

    }
}
