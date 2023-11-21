package com.example;

import com.example2.dtos.RequestType1;
import com.example2.dtos.RequestUndefined;
import com.example2.dtos.RequestWrapper;
import com.example2.dtos.commons.OutputSettings;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.serde.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import java.io.IOException;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
class MicronautSerializerJsonTypeInfoTest {

    @Inject
    EmbeddedApplication<?> application;
    @Inject
    ObjectMapper objectMapper;

    @Test
    void testItWorks() {
        assertTrue(application.isRunning());
    }


    @Test
    @DisplayName("Test try to initialize java object map to json, then revert it")
    void doTest1() throws IOException {
        var source = new RequestWrapper(
                new RequestType1(
                        "lol", "lol", new OutputSettings()
                )
        );
        var tmp = objectMapper.writeValueAsString(source);
        var received = objectMapper.readValue(tmp, RequestWrapper.class);
        assertEquals(source, received);
    }

    @Test
    @DisplayName("Try to initialize Unknown request from json in resource classpath")
    void doTest2() throws IOException {
        RequestWrapper source = objectMapper.readValue(
                getClass().getClassLoader().getResourceAsStream("unknown-request.json"),
                RequestWrapper.class
        );

        assertNotNull(source);
        assertTrue((source.request() instanceof RequestUndefined));
    }

}
