package net.infumia.frame.view.config.option;

import java.util.Objects;
import java.util.StringJoiner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class ViewConfigOptionImpl<T> implements ViewConfigOption<T> {

    @NotNull
    private final String key;

    @Nullable
    private final T defaultValue;

    ViewConfigOptionImpl(@NotNull final String key, @Nullable final T defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    @NotNull
    @Override
    public String key() {
        return this.key;
    }

    @Nullable
    @Override
    public T defaultValue() {
        return this.defaultValue;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.key);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ViewConfigOption<?>)) {
            return false;
        }
        return Objects.equals(this.key, ((ViewConfigOption<?>) o).key());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ViewConfigOptionImpl.class.getSimpleName() + "[", "]")
            .add("key='" + this.key + "'")
            .add("defaultValue=" + this.defaultValue)
            .toString();
    }
}
