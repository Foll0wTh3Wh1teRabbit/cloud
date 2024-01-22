package ru.nsu.cloud.manager.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultListener {

    @RabbitListener(queues = "executor2manager")
    public void consumeResult(String resultMessage) {

    }

}
