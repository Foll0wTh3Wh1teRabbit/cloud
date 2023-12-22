package ru.nsu.cloud.executor.health;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.nsu.cloud.model.health.HealthCheckNodeInformation;
import ru.nsu.cloud.executor.configuration.ExecutorConfiguration;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class HealthCheckNotifier {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    @Value("${server.port}")
    private String port;

    @Scheduled(cron = "0/5 * * * * *")
    public void sendHealthCheckMessage() throws JsonProcessingException {
        log.info("sendHealthCheckMessage <-");

        HealthCheckNodeInformation healthCheckNodeInformation = HealthCheckNodeInformation.builder()
            .workingHost(InetAddress.getLoopbackAddress().getHostName())
            .workingPort(port)
            .build();

        String serializedMessage = objectMapper.writeValueAsString(healthCheckNodeInformation);

        rabbitTemplate.send(
            ExecutorConfiguration.EXCHANGE,
            ExecutorConfiguration.ROUTING_KEY,
            new Message(
                serializedMessage.getBytes(StandardCharsets.UTF_8)
            )
        );
    }

}
