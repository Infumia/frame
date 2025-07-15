package net.infumia.frame.config;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import net.infumia.frame.context.view.ContextClick;
import net.infumia.frame.type.InvType;
import net.infumia.frame.view.config.ViewConfigModifier;
import net.infumia.frame.view.config.option.ViewConfigOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

final class ViewConfigImpl implements ViewConfigRich {

    final Map<ViewConfigOption<Object>, Object> options;
    final Collection<ViewConfigModifier> modifiers;
    final Object title;
    final int size;
    final InvType type;
    final String[] layout;
    final Duration updateInterval;
    final Duration interactionDelay;
    final Consumer<ContextClick> onInteractionDelay;

    ViewConfigImpl(@NotNull final ViewConfigBuilderImpl builder) {
        this.options = Collections.unmodifiableMap(builder.options);
        this.modifiers = Collections.unmodifiableCollection(builder.modifiers);
        this.title = builder.title;
        this.size = builder.size;
        this.type = builder.type;
        this.layout = builder.layout;
        this.updateInterval = builder.updateInterval;
        this.interactionDelay = builder.interactionDelay;
        this.onInteractionDelay = builder.onInteractionDelay;
    }

    @Nullable
    @Override
    public Object title() {
        return this.title;
    }

    @Override
    public int size() {
        return this.size;
    }

    @NotNull
    @Override
    public String@Nullable [] layout() {
        return this.layout;
    }

    @NotNull
    @Override
    public InvType type() {
        return this.type;
    }

    @Nullable
    @Override
    public Duration updateInterval() {
        return this.updateInterval;
    }

    @Nullable
    @Override
    public Duration interactionDelay() {
        return this.interactionDelay;
    }

    @Nullable
    @Override
    public Consumer<ContextClick> onInteractionDelay() {
        return this.onInteractionDelay;
    }

    @NotNull
    @Override
    @UnmodifiableView
    public Map<ViewConfigOption<Object>, Object> options() {
        return this.options;
    }

    @NotNull
    @Override
    @UnmodifiableView
    public Collection<ViewConfigModifier> modifiers() {
        return this.modifiers;
    }

    @NotNull
    @Override
    public ViewConfigBuilderRich toBuilder() {
        return new ViewConfigBuilderImpl(this);
    }

    @Override
    @NotNull
    @SuppressWarnings("unchecked")
    public <T> Optional<T> option(@NotNull final ViewConfigOption<T> option) {
        return Optional.ofNullable(this.options.get(option)).map(value -> (T) value);
    }

    @NotNull
    @Override
    public <T> Optional<T> optionOrDefault(@NotNull final ViewConfigOption<T> option) {
        final Optional<T> value = this.option(option);
        if (value.isPresent()) {
            return value;
        } else {
            return Optional.ofNullable(option.defaultValue());
        }
    }
}
