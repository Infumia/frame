package net.infumia.inv.core.slot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import net.infumia.inv.api.element.ElementItemBuilder;
import net.infumia.inv.api.slot.LayoutSlot;
import net.infumia.inv.api.util.Preconditions;
import net.infumia.inv.core.InvTypeRich;
import net.infumia.inv.core.SlotConverter;
import net.infumia.inv.core.context.view.ContextRenderRich;
import net.infumia.inv.core.element.ElementItemBuilderRich;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SlotFinder {

    private final List<BiFunction<Integer, Integer, ElementItemBuilder>> availableSlotFinders =
        new ArrayList<>();
    private final Collection<ElementItemBuilderRich> nonRenderedBuilders = new ArrayList<>();
    private final ContextRenderRich context;

    public SlotFinder(@NotNull final ContextRenderRich context) {
        this.context = context;
    }

    @Nullable
    public LayoutSlot findLayoutSlot(final char character) {
        return this.context.layouts().get(character);
    }

    public int findFirstSlot() {
        return this.context.container().firstSlot();
    }

    public int findLastSlot() {
        return this.context.container().lastSlot();
    }

    public int findResultSlot() {
        final InvTypeRich type = this.context.container().typeRich();
        final int[] slots = type.resultSlots();
        Preconditions.state(
            slots.length != 0,
            "No result slot available for type '%s'",
            type.type()
        );
        Preconditions.state(slots.length == 1, "Multiple result slots not supported right now!");
        return slots[0];
    }

    public int toSlot(final int row, final int column) {
        return SlotConverter.convertSlot(
            row,
            column,
            this.context.container().rowsCount(),
            this.context.container().columnsCount()
        );
    }

    @NotNull
    public List<BiFunction<Integer, Integer, ElementItemBuilder>> availableSlotFinders() {
        return Collections.unmodifiableList(this.availableSlotFinders);
    }

    @NotNull
    public Collection<ElementItemBuilderRich> nonRenderedBuilders() {
        return this.nonRenderedBuilders;
    }

    public void addAvailableSlotFinder(
        @NotNull final BiFunction<Integer, Integer, ElementItemBuilder> indexAndSlotToBuilder
    ) {
        this.availableSlotFinders.add(indexAndSlotToBuilder);
    }

    public void addNonRenderedBuilder(@NotNull final ElementItemBuilderRich builder) {
        this.nonRenderedBuilders.add(builder);
    }
}
