package ru.nsu.cloud.core.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.nsu.cloud.model.task.FunctionTask;
import ru.nsu.cloud.model.task.Task;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NsuCloudClusterHttpClient {

    private static final String HTTP_PREFIX = "http://";

    private static final String TASK_SYNC_ENDPOINT = "/task";

    private static final String TASK_ASYNC_ENDPOINT = "/task/async";

    private final RestTemplate restTemplate;

    public <T, R> R sendTask(FunctionTask<T, R> task, String host) {
        return restTemplate.exchange(
            HTTP_PREFIX + host + TASK_SYNC_ENDPOINT,
            HttpMethod.POST,
            new HttpEntity<>(task, new HttpHeaders()),
            new ParameterizedTypeReference<R>(){},
            Map.of()
        ).getBody();
    }

    public <T> String sendTaskAsync(Task<T> task, String host) {
        return restTemplate.exchange(
            HTTP_PREFIX + host + TASK_ASYNC_ENDPOINT,
            HttpMethod.POST,
            new HttpEntity<>(task, new HttpHeaders()),
            String.class,
            Map.of()
        ).getBody();
    }

}
