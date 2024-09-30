package net.infumia.inv.api.pipeline.context;

import java.util.Collection;
import net.infumia.inv.api.InventoryManager;
import net.infumia.inv.api.pipeline.PipelineContext;
import net.infumia.inv.api.view.View;
import org.jetbrains.annotations.NotNull;

public interface PipelineContextManager extends PipelineContext {
    @NotNull
    InventoryManager manager();

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
