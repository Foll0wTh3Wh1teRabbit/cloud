package ru.nsu.messaging.healthcheck;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecutorsHealthCheckerListener {

    private final ExecutorsHealthChecker executorsHealthChecker;

    @RabbitListener(queues = "healthcheck")
    public void healthCheckReceive(String healthCheckMessage) {
        log.info("healthCheckReceive <- healthCheckMessage:{}", healthCheckMessage);

        //preprocess msg

        executorsHealthChecker.healthCheckAccept();
    }

}
