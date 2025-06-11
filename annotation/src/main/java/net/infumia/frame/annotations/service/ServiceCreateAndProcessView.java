package net.infumia.frame.annotations.service;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import net.infumia.frame.Frame;
import net.infumia.frame.annotations.AnnotationProcessor;
import net.infumia.frame.pipeline.context.PipelineContextFrame;
import net.infumia.frame.service.Service;
import net.infumia.frame.view.View;
import org.jetbrains.annotations.NotNull;

public final class ServiceCreateAndProcessView
    implements Service<PipelineContextFrame.CreateViews, CompletableFuture<Collection<Object>>> {

    private final AnnotationProcessor annotationProcessor;

    public ServiceCreateAndProcessView(@NotNull final AnnotationProcessor annotationProcessor) {
        this.annotationProcessor = annotationProcessor;
    }

    @NotNull
    @Override
    public CompletableFuture<Collection<Object>> apply(
        @NotNull final PipelineContextFrame.CreateViews context
    ) {
        final Frame frame = context.frame();
        final Collection<CompletableFuture<Object>> futures = context
            .rawViews()
            .stream()
            .map(viewClass ->
                frame
                    .viewFactory()
                    .create(viewClass)
                    .thenApply(view -> {
                        this.annotationProcessor.process((View) view);
                        return view;
                    })
            )
            .collect(Collectors.toList());
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenApply(v ->
            futures.stream().map(CompletableFuture::join).collect(Collectors.toList())
        );
    }
}
