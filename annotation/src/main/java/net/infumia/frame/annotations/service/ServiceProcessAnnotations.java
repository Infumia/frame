package net.infumia.frame.annotations.service;

import net.infumia.frame.annotations.AnnotationProcessor;
import net.infumia.frame.context.view.ContextInit;
import net.infumia.frame.service.ConsumerService;
import org.jetbrains.annotations.NotNull;

public final class ServiceProcessAnnotations implements ConsumerService<ContextInit> {

    public static final String KEY = "process-annotations";

    private final AnnotationProcessor annotationProcessor;

    public ServiceProcessAnnotations(@NotNull final AnnotationProcessor annotationProcessor) {
        this.annotationProcessor = annotationProcessor;
    }

    @Override
    public String key() {
        return ServiceProcessAnnotations.KEY;
    }

    @Override
    public void accept(@NotNull final ContextInit context) {
        this.annotationProcessor.process(context.view());
    }
}
