package net.infumia.frame.pipeline.service.render;

import java.util.Set;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.element.item.ElementItemBuilder;
import net.infumia.frame.element.item.ElementItemBuilderRich;
import net.infumia.frame.element.pagination.ElementPagination;
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
        final Set<Character> paginationLayouts = ctx
            .elements()
            .stream()
            .filter(ElementPagination.class::isInstance)
            .map(ElementPagination.class::cast)
            .map(ElementPagination::layout)
            .collect(Collectors.toSet());
        for (final LayoutSlot layout : context.layouts()) {
            final IntFunction<ElementItemBuilder> builderFactory = layout.builderFactory();
            if (builderFactory == null) {
                continue;
            }

            if (paginationLayouts.contains(layout.character())) {
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
