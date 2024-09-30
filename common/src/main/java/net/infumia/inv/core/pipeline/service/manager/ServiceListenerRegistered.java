package net.infumia.inv.core.pipeline.service.manager;

import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextManager;
import net.infumia.inv.core.InventoryManagerRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceListenerRegistered
    implements PipelineServiceConsumer<PipelineContextManager.ListenerRegistered> {

    public static final PipelineServiceConsumer<
        PipelineContextManager.ListenerRegistered
    > INSTANCE = new ServiceListenerRegistered();

    public static final String KEY = "register";

    @Override
    public String key() {
        return ServiceListenerRegistered.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextManager.ListenerRegistered ctx) {
        ((InventoryManagerRich) ctx.manager()).listener().register();
    }

    private ServiceListenerRegistered() {}
}
