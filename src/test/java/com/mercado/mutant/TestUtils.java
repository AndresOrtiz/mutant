package com.mercado.mutant;

import org.mockito.stubbing.Answer;
import reactor.core.publisher.Mono;

public class TestUtils {

    public static <T> Answer<Object> getMonoFromFirstArgument() {
        return invocation ->
                Mono.just((T) invocation.getArguments()[0]);
    }

    private TestUtils() {
    }

}
