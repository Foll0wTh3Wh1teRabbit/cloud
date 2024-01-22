package ru.nsu.cloud.core.loadbalance.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class PreflightHealthCheckerImpl implements PreflightHealthChecker {

    private static final String HTTP_PREFIX = "http://";

    private static final String HEALTH_ENDPOINT = "/health";

    private final RestTemplate restTemplate;

    @Override
    public boolean sendPreflightRequest(String host) {
        try {
            return restTemplate.exchange(
                HTTP_PREFIX + host + HEALTH_ENDPOINT,
                HttpMethod.POST,
                null,
                String.class
            ).getStatusCode().is2xxSuccessful();
        } catch (RestClientException e) {
            return false;
        }
    }

}
