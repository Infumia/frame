package net.infumia.frame.slot;

import java.util.Arrays;
import java.util.function.IntFunction;
import net.infumia.frame.element.ElementItemBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LayoutSlotImpl implements LayoutSlot {

    private final char character;
    private final int@NotNull[] slots;
    private IntFunction<ElementItemBuilder> builderFactory;

    public LayoutSlotImpl(final char character, final int@NotNull[] slots) {
        this.character = character;
        this.slots = slots;
    }

    @Override
    public char character() {
        return this.character;
    }

    @Override
    public int@NotNull[] slots() {
        return this.slots;
    }

    @Nullable
    @Override
    public IntFunction<ElementItemBuilder> builderFactory() {
        return this.builderFactory;
    }

    @Override
    public void builderFactory(@Nullable final IntFunction<ElementItemBuilder> builderFactory) {
        this.builderFactory = builderFactory;
    }

    @Override
    public boolean contains(final int slot) {
        return Arrays.stream(this.slots).anyMatch(i -> i == slot);
    }
}
