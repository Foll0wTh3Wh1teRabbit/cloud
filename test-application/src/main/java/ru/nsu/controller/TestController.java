package ru.nsu.controller;

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
    public void testMethod() {
        testService.executeLocal();
        testService.executeRemote();
    }

}
