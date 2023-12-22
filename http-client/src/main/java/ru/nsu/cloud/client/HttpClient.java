package ru.nsu.cloud.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
public class HttpClient {

    private final RestTemplate restTemplate;

    public void performHealthCheckRequest(String address) {

    }

}
