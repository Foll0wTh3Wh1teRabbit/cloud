package ru.nsu.cloud.manager.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.nsu.cloud.manager.configuration.ManagerConfiguration;
import ru.nsu.cloud.model.result.Answer;
import ru.nsu.cloud.model.task.Task;
import ru.nsu.cloud.model.task.TaskWithId;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskSender {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    private final HazelcastInstance hazelcastInstance;

    public <T> void sendTask(TaskWithId<T> taskWithId, String routingKeyToSend) {
        hazelcastInstance.getCPSubsystem().getLock(ManagerConfiguration.UPDATE_TASKS_LOCK).lock();

        try {
            String serializedMessage = objectMapper.writeValueAsString(taskWithId);

            Message message = new Message(serializedMessage.getBytes(StandardCharsets.UTF_8));

            rabbitTemplate.send(
                ManagerConfiguration.EXCHANGE,
                routingKeyToSend,
                message
            );

            hazelcastInstance.<String, Task<T>> getMap(ManagerConfiguration.PROCESSING_TASKS_MAP)
                .set(taskWithId.getTaskId(), taskWithId.getTask());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Serialization error");
        } finally {
            hazelcastInstance.getCPSubsystem().getLock(ManagerConfiguration.UPDATE_TASKS_LOCK).unlock();
        }
    }

    public <T, R> R sendTaskAndAwaitForResult(TaskWithId<T> taskWithId, String routingKeyToSend) {
        hazelcastInstance.getCPSubsystem().getLock(ManagerConfiguration.UPDATE_TASKS_LOCK).lock();

        try {
            String serializedMessage = objectMapper.writeValueAsString(taskWithId);

            hazelcastInstance.<String, Task<T>> getMap(ManagerConfiguration.PROCESSING_TASKS_MAP)
                .set(taskWithId.getTaskId(), taskWithId.getTask());

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
        } finally {
            hazelcastInstance.getCPSubsystem().getLock(ManagerConfiguration.UPDATE_TASKS_LOCK).unlock();
        }
    }

}
