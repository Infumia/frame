package net.infumia.frame.annotations.service;

import net.infumia.frame.annotations.AnnotationProcessor;
import net.infumia.frame.context.view.ContextInit;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.service.Service;
import org.jetbrains.annotations.NotNull;

public final class ServiceProcessAnnotations
    implements Service<ContextInit, ConsumerService.State> {

    private final AnnotationProcessor annotationProcessor;

    public ServiceProcessAnnotations(@NotNull final AnnotationProcessor annotationProcessor) {
        this.annotationProcessor = annotationProcessor;
    }

    @NotNull
    @Override
    public ConsumerService.State apply(@NotNull final ContextInit context) {
        this.annotationProcessor.process(context.view());
        return ConsumerService.State.CONTINUE;
    }
}
