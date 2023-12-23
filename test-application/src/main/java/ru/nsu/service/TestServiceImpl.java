package ru.nsu.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.cloud.core.remote.Remote;

@Slf4j
@Service
public class TestServiceImpl implements TestService {

    @Remote
    public void executeRemote() {
        log.info("executeRemote <-");
    }

    public void executeLocal() {
        log.info("executeLocal <-");
    }

}
