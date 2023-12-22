package ru.nsu.service;

import org.springframework.stereotype.Service;
import ru.nsu.cloud.core.remote.Remote;

@Service
public class TestServiceImpl implements TestService {

    @Remote
    public void executeRemote() {
        System.out.println(1);
    }

    public void executeLocal() {
        System.out.println(2);
    }

}
