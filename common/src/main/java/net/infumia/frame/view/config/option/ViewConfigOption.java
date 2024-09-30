package net.infumia.frame.view.config.option;

import net.infumia.frame.util.Keyed;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ViewConfigOption<T> extends Keyed<String> {
    @NotNull
    static <T> ViewConfigOption<T> create(
        @NotNull final String key,
        @Nullable final T defaultValue
    ) {
        return new ViewConfigOptionImpl<>(key, defaultValue);
    }

    @NotNull
    static <T> ViewConfigOption<T> create(@NotNull final String key) {
        return ViewConfigOption.create(key, null);
    }

    @Nullable
    T defaultValue();
}
