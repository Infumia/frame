package net.infumia.frame.pipeline.service.render;

import java.util.function.IntFunction;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.element.item.ElementItemBuilder;
import net.infumia.frame.element.item.ElementItemBuilderRich;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextRender;
import net.infumia.frame.slot.LayoutSlot;
import org.jetbrains.annotations.NotNull;

public final class ServiceFirstRenderLayout
    implements PipelineServiceConsumer<PipelineContextRender.FirstRender> {

    public static final PipelineServiceConsumer<PipelineContextRender.FirstRender> INSTANCE =
        new ServiceFirstRenderLayout();

    public static final String KEY = "layout";

    @NotNull
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
            int index = 0;
            for (final int slot : layout.slots()) {
                ctx.addElement(
                    ((ElementItemBuilderRich) builderFactory.apply(index).slot(slot)).build(context)
                );
                index++;
            }
        }
    }

    private ServiceFirstRenderLayout() {}
}
