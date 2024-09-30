package net.infumia.inv.core.pipeline.service.element;

import net.infumia.inv.api.context.element.ContextElementClick;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextElement;
import org.jetbrains.annotations.NotNull;

public final class ServiceClickCloseOnClick
    implements PipelineServiceConsumer<PipelineContextElement.Click> {

    public static final PipelineServiceConsumer<PipelineContextElement.Click> INSTANCE =
        new ServiceClickCloseOnClick();

    public static final String KEY = "close-on-click";

    @Override
    public String key() {
        return ServiceClickCloseOnClick.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextElement.Click ctx) {
        final ContextElementClick context = ctx.context();
        if (context.element().closeOnClick()) {
            context.closeForViewer();
        }
    }

    private ServiceClickCloseOnClick() {}
}
