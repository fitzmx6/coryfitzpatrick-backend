package com.api.coryfitzpatrick.unit;

import com.api.coryfitzpatrick.Application;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ApplicationTests {

    @MockitoBean
    private Application application;

    @Test
    void application_contextLoads() {
        assertNotNull(application);
    }
}
