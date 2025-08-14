package net.infumia.frame.slot;

import java.util.function.IntFunction;
import net.infumia.frame.element.item.ElementItemBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface LayoutSlot {
    char character();

    int@NotNull[] slots();

    boolean isDefinedByUser();

    @Nullable
    IntFunction<ElementItemBuilder> builderFactory();

    @NotNull
    LayoutSlot withBuilderFactory(@Nullable IntFunction<ElementItemBuilder> builderFactory);

    boolean contains(int slot);
}
