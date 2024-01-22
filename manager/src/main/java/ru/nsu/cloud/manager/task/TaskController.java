package ru.nsu.cloud.manager.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.cloud.model.task.FunctionTask;
import ru.nsu.cloud.model.task.MapTask;
import ru.nsu.cloud.model.task.Task;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping(value = "/task/async")
    public <T> ResponseEntity<String> receiveTaskAsync(@RequestBody Task<T> task) {
        log.info("receiveTaskAsync <- task: {}", task);

        String taskUuid = UUID.randomUUID().toString();

        CompletableFuture.runAsync(
            () -> taskService.processTask(task, taskUuid)
        );

        log.info("receiveTaskAsync -> taskId: {}", taskUuid);

        return ResponseEntity.ok(taskUuid);
    }

    @PostMapping(value = "/task")
    public <T, R> R receiveTask(@RequestBody FunctionTask<T, R> task) {
        log.info("receiveTask <- task: {}", task);

        String taskUuid = UUID.randomUUID().toString();

        return taskService.processTaskAndReceiveResult(task, taskUuid);
    }

    /*@GetMapping(value = "/task/info/{id}")
    public ResponseEntity<> getTaskInformation(@PathVariable("id") String id) {

    }*/

}
