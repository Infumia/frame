package net.infumia.frame.pipeline.service.render;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.context.view.ContextRenderRich;
import net.infumia.frame.element.ElementBuilderRich;
import net.infumia.frame.element.ElementItemBuilder;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextRender;
import net.infumia.frame.slot.LayoutSlot;
import net.infumia.frame.view.ViewContainerRich;
import org.jetbrains.annotations.NotNull;

public final class ServiceFirstRenderAvailableSlotResolution
    implements PipelineServiceConsumer<PipelineContextRender.FirstRender> {

    private static final char AVAILABLE_SLOT = ' ';

    public static final PipelineServiceConsumer<PipelineContextRender.FirstRender> INSTANCE =
        new ServiceFirstRenderAvailableSlotResolution();

    public static final String KEY = "available-slot-resolution";

    @NotNull
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
        final Collection<LayoutSlot> layouts = context.layouts();
        final LayoutSlot layout = layouts
            .stream()
            .filter(
                slot -> slot.character() == ServiceFirstRenderAvailableSlotResolution.AVAILABLE_SLOT
            )
            .findFirst()
            .orElse(null);
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
        @NotNull final ContextRender context,
        final int slot
    ) {
        return (
            !((ViewContainerRich) context.container()).typeRich().canPlayerInteractOn(slot) ||
            context.container().hasItem(slot) ||
            ((ContextRenderRich) context).slotFinder()
                .nonRenderedBuilders()
                .stream()
                .anyMatch(builder -> builder.slot() == slot)
        );
    }

    private ServiceFirstRenderAvailableSlotResolution() {}
}
