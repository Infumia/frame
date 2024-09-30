package net.infumia.inv.core.pipeline.service.render;

import java.util.Iterator;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextRender;
import net.infumia.inv.core.context.view.ContextRenderRich;
import net.infumia.inv.core.element.ElementItemBuilderRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceFirstRenderConsumeNonRenderedElement
    implements PipelineServiceConsumer<PipelineContextRender.FirstRender> {

    public static final PipelineServiceConsumer<PipelineContextRender.FirstRender> INSTANCE =
        new ServiceFirstRenderConsumeNonRenderedElement();

    public static final String KEY = "consume-non-rendered-element";

    @Override
    public String key() {
        return ServiceFirstRenderConsumeNonRenderedElement.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextRender.FirstRender ctx) {
        final ContextRenderRich context = (ContextRenderRich) ctx.context();
        final Iterator<ElementItemBuilderRich> iterator = context
            .slotFinder()
            .nonRenderedBuilders()
            .iterator();
        while (iterator.hasNext()) {
            ctx.addElement(iterator.next().build(context));
            iterator.remove();
        }
    }

    private ServiceFirstRenderConsumeNonRenderedElement() {}
}
