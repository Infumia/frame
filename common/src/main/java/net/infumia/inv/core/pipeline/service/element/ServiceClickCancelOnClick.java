package net.infumia.inv.core.pipeline.service.element;

import net.infumia.inv.api.context.element.ContextElementClick;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextElement;
import org.jetbrains.annotations.NotNull;

public final class ServiceClickCancelOnClick
    implements PipelineServiceConsumer<PipelineContextElement.Click> {

    public static final PipelineServiceConsumer<PipelineContextElement.Click> INSTANCE =
        new ServiceClickCancelOnClick();

    public static final String KEY = "cancel-on-click";

    @Override
    public String key() {
        return ServiceClickCancelOnClick.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextElement.Click ctx) {
        final ContextElementClick context = ctx.context();
        if (context.element().cancelOnClick()) {
            context.cancelled(true);
        }
    }

    private ServiceClickCancelOnClick() {}
}
