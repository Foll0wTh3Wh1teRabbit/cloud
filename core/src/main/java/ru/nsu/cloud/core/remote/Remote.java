package ru.nsu.cloud.core.remote;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Remote {

    int cpu() default 0;

    int gpu() default 0;

    int timeout() default 30000;

}
