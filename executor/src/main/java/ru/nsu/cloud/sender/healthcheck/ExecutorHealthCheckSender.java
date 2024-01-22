package ru.nsu.cloud.sender.healthcheck;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.nsu.cloud.model.health.HealthCheckExecutorInformation;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecutorHealthCheckSender {

    private static final String EXCHANGE = "cloudExchange";

    private static final String HEALTHCHECK_ROUTING_KEY = "healthcheck";


    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    private String executorId;


    @Value("${nsucloud.executor.instanceName}")
    private String instanceName;


    @PostConstruct
    public void init() {
        this.executorId = UUID.randomUUID().toString();
    }

    @Scheduled(cron = " 0/5 * * * * * ")
    public void healthCheckSend() throws JsonProcessingException {
        log.trace("Sending healthcheck message, id: {}", executorId);

        HealthCheckExecutorInformation information = HealthCheckExecutorInformation.builder()
            .instanceName(instanceName)
            .instanceId(executorId)
            .build();

        String serializedMessage = objectMapper.writeValueAsString(information);

        rabbitTemplate.send(
            EXCHANGE,
            HEALTHCHECK_ROUTING_KEY,
            new Message(serializedMessage.getBytes(StandardCharsets.UTF_8))
        );
    }

}
