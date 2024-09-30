package net.infumia.frame.slot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import net.infumia.frame.InvTypeRich;
import net.infumia.frame.SlotConverter;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.element.ElementItemBuilder;
import net.infumia.frame.element.ElementItemBuilderRich;
import net.infumia.frame.util.Preconditions;
import net.infumia.frame.view.ViewContainerRich;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SlotFinder {

    private final List<BiFunction<Integer, Integer, ElementItemBuilder>> availableSlotFinders =
        new ArrayList<>();
    private final Collection<ElementItemBuilderRich> nonRenderedBuilders = new ArrayList<>();
    private final ContextRender context;

    public SlotFinder(@NotNull final ContextRender context) {
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
        final InvTypeRich type = ((ViewContainerRich) this.context.container()).typeRich();
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
