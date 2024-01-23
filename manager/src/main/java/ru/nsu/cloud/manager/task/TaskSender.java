package ru.nsu.cloud.manager.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.nsu.cloud.manager.configuration.ManagerConfiguration;
import ru.nsu.cloud.model.task.TaskWithId;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskSender {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    public <T> void sendTask(TaskWithId<T> taskWithId, String routingKeyToSend) {
        try {
            String serializedMessage = objectMapper.writeValueAsString(taskWithId);

            Message message = new Message(serializedMessage.getBytes(StandardCharsets.UTF_8));

            rabbitTemplate.send(
                ManagerConfiguration.EXCHANGE,
                routingKeyToSend,
                message
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Serialization error");
        }
    }

    public <T, R> R sendTaskAndAwaitForResult(TaskWithId<T> taskWithId, String routingKeyToSend) {
        try {
            String serializedMessage = objectMapper.writeValueAsString(taskWithId);

            Message message = new Message(serializedMessage.getBytes(StandardCharsets.UTF_8));

            message.getMessageProperties().setReplyTo("executor2manager");
            message.getMessageProperties().setCorrelationId(taskWithId.getTaskId());

            R response = (R) rabbitTemplate.convertSendAndReceive(
                ManagerConfiguration.EXCHANGE,
                routingKeyToSend,
                message
            );

            return Optional.ofNullable(response)
                .orElseThrow(() -> new RuntimeException("Null result"));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Serialization error");
        }
    }

}
