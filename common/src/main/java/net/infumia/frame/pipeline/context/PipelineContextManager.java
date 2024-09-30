package net.infumia.frame.pipeline.context;

import java.util.Collection;
import java.util.function.Consumer;
import net.infumia.frame.Frame;
import net.infumia.frame.pipeline.PipelineContext;
import net.infumia.frame.typedkey.TypedKeyStorageImmutableBuilder;
import net.infumia.frame.view.View;
import org.jetbrains.annotations.NotNull;

public interface PipelineContextManager extends PipelineContext {
    @NotNull
    Frame frame();

    interface ViewCreated extends PipelineContextManager {
        @NotNull
        Collection<Class<?>> registeredViews();
    }

    interface ViewRegistered extends PipelineContextManager {
        @NotNull
        Collection<Object> registeredViews();

        @NotNull
        Consumer<TypedKeyStorageImmutableBuilder> instanceConfigurer();
    }

    interface ListenerRegistered extends PipelineContextManager {}

    interface ViewUnregistered extends PipelineContextManager {
        @NotNull
        Collection<View> unregisteredViews();
    }
}
