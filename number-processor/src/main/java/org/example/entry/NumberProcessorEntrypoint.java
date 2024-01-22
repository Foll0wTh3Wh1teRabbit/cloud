package org.example.entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.process.NumberProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.nsu.cloud.model.task.FunctionTaskWithId;
import ru.nsu.cloud.sender.answer.ExecutorAnswerSender;
import ru.nsu.cloud.model.result.Answer;
import ru.nsu.cloud.model.task.TaskWithId;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NumberProcessorEntrypoint {

    private final ObjectMapper objectMapper;

    private final NumberProcessor numberProcessor;

    @RabbitListener(queues = "manager2NumberProcessor")
    public Long entrypoint(String taskWithId) {
        FunctionTaskWithId<List<Long>, Long> task;
        List<Long> numbers;

        try {
            task = objectMapper.readValue(taskWithId, FunctionTaskWithId.class);

            numbers = Arrays.stream(
                objectMapper.readValue(
                    task.getTask().getSerializedData(),
                    Long[].class
                )
            ).toList();
        } catch (JsonProcessingException ignored) {
            throw new RuntimeException("Deserialization error");
        }

        log.info("Received task, taskWithId: {}", taskWithId);

        Long result = numberProcessor.processNumbers(numbers);

        log.trace("Calculated result: {}", result);

        return result;
    }

    @RabbitListener(queues = "manager2NumberProcessorAsync")
    public void entrypointAsync(String taskWithId) {
        TaskWithId<Long> task;
        List<Long> numbers;

        try {
            task = objectMapper.readValue(taskWithId, TaskWithId.class);

            numbers = Arrays.stream(
                objectMapper.readValue(
                    task.getTask().getSerializedData(),
                    Long[].class
                )
            ).toList();
        } catch (JsonProcessingException ignored) {
            throw new RuntimeException("Deserialization error");
        }

        log.info("Received task, taskWithId: {}", taskWithId);

        Long result = numberProcessor.processNumbers(numbers);

        log.trace("Calculated result: {}", result);
    }

}
