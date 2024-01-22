package ru.nsu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.service.TestService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping(value = "/test")
    public void testMethod1() throws JsonProcessingException {
        testService.executeRemote();
    }

    @PostMapping(value = "/test/value")
    public void testMethod2() throws JsonProcessingException {
        testService.executeRemoteWithValue();
    }

    @PostMapping(value = "/test/map")
    public void testMethod3() throws JsonProcessingException {
        testService.executeRemoteWithMap();
    }

    @PostMapping(value = "/test/map/value")
    public void testMethod4() throws JsonProcessingException {
        testService.executeRemoteWithMapAndValue();
    }

}
