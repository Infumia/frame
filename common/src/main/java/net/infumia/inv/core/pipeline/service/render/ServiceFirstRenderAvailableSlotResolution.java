package net.infumia.inv.core.pipeline.service.render;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import net.infumia.inv.api.element.ElementItemBuilder;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextRender;
import net.infumia.inv.api.slot.LayoutSlot;
import net.infumia.inv.core.context.view.ContextRenderRich;
import net.infumia.inv.core.element.ElementBuilderRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceFirstRenderAvailableSlotResolution
    implements PipelineServiceConsumer<PipelineContextRender.FirstRender> {

    private static final char AVAILABLE_SLOT = ' ';

    public static final PipelineServiceConsumer<PipelineContextRender.FirstRender> INSTANCE =
        new ServiceFirstRenderAvailableSlotResolution();

    public static final String KEY = "available-slot-resolution";

    @Override
    public String key() {
        return ServiceFirstRenderAvailableSlotResolution.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextRender.FirstRender ctx) {
        if (ctx.context().layouts().isEmpty()) {
            this.fromInitialSlot(ctx);
        } else {
            this.fromAvailableSlots(ctx);
        }
    }

    private void fromAvailableSlots(@NotNull final PipelineContextRender.FirstRender ctx) {
        final ContextRenderRich context = (ContextRenderRich) ctx.context();
        final Map<Character, LayoutSlot> layouts = context.layouts();
        final LayoutSlot layout = layouts.get(
            ServiceFirstRenderAvailableSlotResolution.AVAILABLE_SLOT
        );
        if (layout == null) {
            return;
        }
        final int[] slots = layout.slots();
        if (slots.length == 0) {
            return;
        }
        final List<BiFunction<Integer, Integer, ElementItemBuilder>> availableSlotFinders = context
            .slotFinder()
            .availableSlotFinders();
        int offset = 0;
        for (int i = 0; i < availableSlotFinders.size(); i++) {
            int slot;
            try {
                slot = slots[i + offset];
            } catch (final IndexOutOfBoundsException e) {
                throw new RuntimeException(
                    "Capacity to accommodate items in the layout for items in available slots has been exceeded."
                );
            }
            while (this.isSlotNotAvailableForAutoFilling(context, slot)) {
                try {
                    slot = slots[i + ++offset];
                } catch (final IndexOutOfBoundsException exception) {
                    throw new RuntimeException(
                        String.format(
                            "Capacity to accommodate items in the layout for items" +
                            " in available slots has been exceeded. " +
                            "Tried to set an item from index %d from position %d to another, " +
                            "but it breaks the layout rules",
                            i,
                            slot
                        )
                    );
                }
            }
            ctx.addElement(
                ((ElementBuilderRich) availableSlotFinders.get(i).apply(i, slot)).build(context)
            );
        }
    }

    private void fromInitialSlot(@NotNull final PipelineContextRender.FirstRender ctx) {
        final ContextRenderRich context = (ContextRenderRich) ctx.context();
        final List<BiFunction<Integer, Integer, ElementItemBuilder>> availableSlotFinders = context
            .slotFinder()
            .availableSlotFinders();
        int slot = 0;
        for (int i = 0; i < context.container().size(); i++) {
            while (this.isSlotNotAvailableForAutoFilling(context, slot)) {
                slot++;
            }
            if (i >= availableSlotFinders.size()) {
                break;
            }
            final BiFunction<Integer, Integer, ElementItemBuilder> factory =
                availableSlotFinders.get(i);
            ctx.addElement(((ElementBuilderRich) factory.apply(i, slot++)).build(context));
        }
    }

    private boolean isSlotNotAvailableForAutoFilling(
        @NotNull final ContextRenderRich context,
        final int slot
    ) {
        return (
            !context.container().typeRich().canPlayerInteractOn(slot) ||
            context.container().hasItem(slot) ||
            context
                .slotFinder()
                .nonRenderedBuilders()
                .stream()
                .anyMatch(builder -> builder.slot() == slot)
        );
    }

    private ServiceFirstRenderAvailableSlotResolution() {}
}
