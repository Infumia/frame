package net.infumia.inv.api.slot;

import java.util.function.IntFunction;
import net.infumia.inv.api.element.ElementItemBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface LayoutSlot {
    char character();

    int@NotNull[] slots();

    @Nullable
    IntFunction<ElementItemBuilder> builderFactory();

    void builderFactory(@Nullable IntFunction<ElementItemBuilder> builderFactory);

    boolean contains(int slot);
}
