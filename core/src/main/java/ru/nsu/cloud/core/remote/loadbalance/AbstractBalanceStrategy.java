package ru.nsu.cloud.core.remote.loadbalance;

import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Predicate;

import static ru.nsu.cloud.model.constant.Constants.HEALTH_ENDPOINT;
import static ru.nsu.cloud.model.constant.Constants.HTTP_PREFIX;

@Setter
public abstract class AbstractBalanceStrategy implements BalanceStrategy {

    protected List<String> hosts;

    protected Predicate<String> checkIfHostIsAvailable = host -> {
        try {
            new RestTemplate().exchange(
                HTTP_PREFIX + host + HEALTH_ENDPOINT,
                HttpMethod.POST,
                null,
                String.class
            );

            return true;
        } catch (RestClientException e) {
            return false;
        }
    };

    public BalanceStrategy instantiate(List<String> hosts) {
        this.hosts = hosts;

        return this;
    }

}
