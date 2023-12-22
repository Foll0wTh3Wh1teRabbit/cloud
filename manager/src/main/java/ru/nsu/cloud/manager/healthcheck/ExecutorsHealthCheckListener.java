package ru.nsu.cloud.manager.healthcheck;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.nsu.cloud.model.HealthCheckMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecutorsHealthCheckListener {

    private final ExecutorsHealthCheckAcceptor executorsHealthCheckAcceptor;

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "healthcheck")
    public void healthCheckReceive(String healthCheckMessage) {
        log.info("healthCheckReceive <- healthCheckMessage:{}", healthCheckMessage);

        HealthCheckMessage deserializedMessage;

        try {
            deserializedMessage = objectMapper.readValue(healthCheckMessage, HealthCheckMessage.class);
        } catch (JsonProcessingException ignored) {
            throw new RuntimeException("Deserialization error");
        }

        executorsHealthCheckAcceptor.healthCheckAccept(deserializedMessage);
    }

}
