package ru.nsu.cloud.executor.health;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.nsu.cloud.model.health.HealthCheckExecutorInformation;
import ru.nsu.cloud.executor.configuration.ExecutorConfiguration;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class HealthCheckNotifier {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    @Value("${server.port}")
    private String port;

    @Value("${nsucloud.executor.cpu}")
    private Integer cpu;

    @Value("${nsucloud.executor.gpu}")
    private Integer gpu;

    @Scheduled(cron = "0/5 * * * * *")
    public void sendHealthCheckMessage() throws JsonProcessingException {
        HealthCheckExecutorInformation healthCheckExecutorInformation = HealthCheckExecutorInformation.builder()
            .workingHost(InetAddress.getLoopbackAddress().getHostName())
            .workingPort(port)
            .cpu(cpu)
            .gpu(gpu)
            .build();

        String serializedMessage = objectMapper.writeValueAsString(healthCheckExecutorInformation);

        rabbitTemplate.send(
            ExecutorConfiguration.EXCHANGE,
            ExecutorConfiguration.ROUTING_KEY,
            new Message(
                serializedMessage.getBytes(StandardCharsets.UTF_8)
            )
        );
    }

}
