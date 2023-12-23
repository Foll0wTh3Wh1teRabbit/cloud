package ru.nsu.cloud.manager.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.cloud.model.task.Task;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping(value = "/task")
    public void receiveTask(@RequestBody Task task) {
        log.info("receiveTask <- task:{}", task);

        taskService.processTask(task);

        log.info("receiveTask ->");
    }

}
