package ru.nsu.cloud.sender.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nsu.cloud.ExecutorConfiguration;
import ru.nsu.cloud.model.result.Result;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecutorResultSenderImpl implements ExecutorResultSender {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    @Value("${nsucloud.executor.instanceName}")
    private String instanceName;

    @Override
    public void sendResult(Result result) {
        try {
            result.setInstance(instanceName);

            String serializedResult = objectMapper.writeValueAsString(result);

            rabbitTemplate.send(
                ExecutorConfiguration.EXCHANGE,
                ExecutorConfiguration.EXECUTOR_2_MANAGER_QUEUE,
                new Message(serializedResult.getBytes(StandardCharsets.UTF_8))
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Serialization error");
        }
    }

}
