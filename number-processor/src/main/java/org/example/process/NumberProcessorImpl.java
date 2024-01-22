package org.example.process;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NumberProcessorImpl implements NumberProcessor {

    @Override
    public Long processNumbers(List<Long> numbers) {
        log.info("processNumbers <-");

        Long sum = 0L;
        for (Long number : numbers) {
            sum += number;
        }

        log.info("Sum of received numbers is {}", sum);

        return sum;
    }

}
