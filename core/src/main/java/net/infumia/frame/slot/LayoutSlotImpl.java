package net.infumia.frame.slot;

import java.util.Arrays;
import java.util.function.IntFunction;
import net.infumia.frame.element.item.ElementItemBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LayoutSlotImpl implements LayoutSlot {

    private final char character;
    private final int@NotNull [] slots;
    private final IntFunction<ElementItemBuilder> builderFactory;

    private LayoutSlotImpl(
        final char character,
        final int@NotNull [] slots,
        @Nullable final IntFunction<ElementItemBuilder> builderFactory
    ) {
        this.character = character;
        this.slots = slots;
        this.builderFactory = builderFactory;
    }

    public LayoutSlotImpl(final char character, final int@NotNull [] slots) {
        this(character, slots, null);
    }

    @Override
    public char character() {
        return this.character;
    }

    @Override
    public int@NotNull [] slots() {
        return this.slots;
    }

    @Nullable
    @Override
    public IntFunction<ElementItemBuilder> builderFactory() {
        return this.builderFactory;
    }

    @NotNull
    @Override
    public LayoutSlot withBuilderFactory(
        @Nullable final IntFunction<ElementItemBuilder> builderFactory
    ) {
        return new LayoutSlotImpl(this.character, this.slots, builderFactory);
    }

    @Override
    public boolean contains(final int slot) {
        return Arrays.stream(this.slots).anyMatch(i -> i == slot);
    }
}
