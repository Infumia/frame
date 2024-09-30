package net.infumia.frame.pipeline.service.render;

import java.util.function.IntFunction;
import java.util.stream.IntStream;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.element.ElementBuilderRich;
import net.infumia.frame.element.ElementItemBuilder;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextRender;
import net.infumia.frame.slot.LayoutSlot;
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
        final ContextRender context = ctx.context();
        for (final LayoutSlot layout : context.layouts()) {
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
