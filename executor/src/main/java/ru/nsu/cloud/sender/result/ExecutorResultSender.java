package ru.nsu.cloud.sender.result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.cloud.model.result.Result;

public interface ExecutorResultSender {

    void sendResult(Result result);

}
