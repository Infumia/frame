package net.infumia.inv.core.pipeline.service.render;

import java.util.function.IntFunction;
import java.util.stream.IntStream;
import net.infumia.inv.api.element.ElementItemBuilder;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextRender;
import net.infumia.inv.api.slot.LayoutSlot;
import net.infumia.inv.core.context.view.ContextRenderRich;
import net.infumia.inv.core.element.ElementBuilderRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceFirstRenderLayout
    implements PipelineServiceConsumer<PipelineContextRender.FirstRender> {

    public static final PipelineServiceConsumer<PipelineContextRender.FirstRender> INSTANCE =
        new ServiceFirstRenderLayout();

    public static final String KEY = "layout";

    @Override
    public String key() {
        return ServiceFirstRenderLayout.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextRender.FirstRender ctx) {
        final ContextRenderRich context = (ContextRenderRich) ctx.context();
        for (final LayoutSlot layout : context.layouts().values()) {
            final IntFunction<ElementItemBuilder> builderFactory = layout.builderFactory();
            if (builderFactory == null) {
                continue;
            }
            final int[] slots = layout.slots();
            IntStream.range(0, slots.length)
                .mapToObj(index ->
                    (ElementBuilderRich) builderFactory.apply(index).slot(slots[index])
                )
                .map(element -> element.build(context))
                .forEach(ctx::addElement);
        }
    }

    private ServiceFirstRenderLayout() {}
}
