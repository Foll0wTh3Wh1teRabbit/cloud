package ru.nsu.cloud.core.remote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.nsu.cloud.core.remote.loadbalance.BalanceStrategy;
import ru.nsu.cloud.core.remote.loadbalance.BalanceStrategyFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static ru.nsu.cloud.model.constant.Constants.HTTP_PREFIX;
import static ru.nsu.cloud.model.constant.Constants.TASK_ENDPOINT;

@Slf4j
@Component
public class RemoteAnnotationBeanPostProcessor implements BeanPostProcessor {

    private BalanceStrategy balanceStrategy;

    @Value("${nsucloud.balancer.strategy}")
    private String strategy;

    @Value("${nsucloud.hosts}")
    private List<String> hosts;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        try {
            balanceStrategy = new BalanceStrategyFactory().getStrategy(strategy).instantiate(hosts);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        boolean hasRemoteMethods = Arrays.stream(bean.getClass().getDeclaredMethods())
            .anyMatch(it -> it.isAnnotationPresent(Remote.class));

        if (hasRemoteMethods) {
            return Proxy.newProxyInstance(
                bean.getClass().getClassLoader(),
                bean.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    Method inheritedMethod = bean.getClass().getMethod(
                        method.getName(),
                        Arrays.stream(args)
                            .map(Object::getClass)
                            .toList()
                            .toArray(new Class<?>[0])
                    );

                    if (inheritedMethod.isAnnotationPresent(Remote.class)) {
                        String requestAddress = balanceStrategy.getNextAddress();
                        Integer callTimeout = inheritedMethod.getAnnotation(Remote.class).timeout();

                        log.info("Remote call to {}", requestAddress);

                        return new RestTemplate().exchange(
                            HTTP_PREFIX + requestAddress + TASK_ENDPOINT,
                            HttpMethod.POST,
                            new HttpEntity<>(
                                null,
                                null
                            ),
                            method.getReturnType()
                        );
                    }

                    return method.invoke(bean, args);
                });
        }

        return bean;
    }

}
