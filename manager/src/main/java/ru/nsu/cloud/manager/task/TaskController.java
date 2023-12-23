package ru.nsu.cloud.manager.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TaskController {

    @PostMapping(value = "/task")
    public void receiveTask() {
        log.info("receiveTask <-");



        log.info("receiveTask ->");
    }

}
