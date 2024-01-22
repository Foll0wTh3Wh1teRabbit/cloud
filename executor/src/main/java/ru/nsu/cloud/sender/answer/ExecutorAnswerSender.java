package ru.nsu.cloud.sender.answer;

import ru.nsu.cloud.model.result.Answer;

public interface ExecutorAnswerSender {

    <T> void sendAnswer(Answer<T> answer);

}
