package com.hurricane.components.sequencer.exception;

import com.hurricane.components.sequencer.configure.annotations.Process;
import com.hurricane.components.sequencer.runtime.Step;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.stream.Collectors;

public class ProcessMethodMissingException extends SequencerException {
    private ProcessMethodMissingException(String message) {
        super(message);
    }

    public static ProcessMethodMissingException notFound(final Step step) {
        return new ProcessMethodMissingException(createNotFoundMessage(step));
    }

    public static ProcessMethodMissingException tooMany(
            final Step step,
            final Collection<Method> processMethods) {
        return new ProcessMethodMissingException(
                createTooManyMessage(step, processMethods));
    }

    private static String createNotFoundMessage(final Step step) {
        return "Unable to find process method within Step class /" +
                step.getClass().getCanonicalName() +
                "/. Remember that method need to be annotated with @" +
                Process.class.getName() + " " +
                "and should be public";
    }

    private static String createTooManyMessage(final Step step, final Collection<Method> processMethods) {
        final String methodNames = processMethods.stream()
                .map(Method::getName)
                .collect(Collectors.joining(", "));
        return "There is to many methods within Step class /" +
                step.getClass().getCanonicalName() +
                "/ annotated with @" +
                Process.class.getName() +
                ". There need to be only one. Found methods: " +
                methodNames;
    }
}
