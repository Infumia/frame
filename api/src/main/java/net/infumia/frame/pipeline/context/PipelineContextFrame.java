package net.infumia.frame.pipeline.context;

import java.util.Collection;
import java.util.function.Consumer;
import net.infumia.frame.Frame;
import net.infumia.frame.typedkey.TypedKeyStorageImmutableBuilder;
import net.infumia.frame.view.View;
import org.jetbrains.annotations.NotNull;

public interface PipelineContextFrame {
    @NotNull
    Frame frame();

    interface CreateViews extends PipelineContextFrame {
        @NotNull
        Collection<Class<?>> rawViews();
    }

    interface RegisterViews extends PipelineContextFrame {
        @NotNull
        Collection<Object> views();

        @NotNull
        Consumer<TypedKeyStorageImmutableBuilder> instanceConfigurer();
    }

    interface RegisterListeners extends PipelineContextFrame {}

    interface UnregisterViews extends PipelineContextFrame {
        @NotNull
        Collection<View> views();
    }
}
