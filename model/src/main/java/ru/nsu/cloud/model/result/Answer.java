package ru.nsu.cloud.model.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(builderMethodName = "answerBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class Answer<T> extends Result {

    private T result;

}
