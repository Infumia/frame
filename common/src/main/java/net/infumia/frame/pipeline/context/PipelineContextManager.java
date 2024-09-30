package net.infumia.frame.pipeline.context;

import java.util.Collection;
import net.infumia.frame.Frame;
import net.infumia.frame.pipeline.PipelineContext;
import net.infumia.frame.view.View;
import org.jetbrains.annotations.NotNull;

public interface PipelineContextManager extends PipelineContext {
    @NotNull
    Frame manager();

    interface ViewCreated extends PipelineContextManager {
        @NotNull
        Collection<Class<?>> registeredViews();
    }

    interface ViewRegistered extends PipelineContextManager {
        @NotNull
        Collection<Object> registeredViews();
    }

    interface ListenerRegistered extends PipelineContextManager {}

    interface ViewUnregistered extends PipelineContextManager {
        @NotNull
        Collection<View> unregisteredViews();
    }
}
