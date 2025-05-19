package net.infumia.frame.view.config;

import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import net.infumia.frame.context.view.ContextClick;
import net.infumia.frame.type.InvType;
import net.infumia.frame.view.config.option.ViewConfigOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public interface ViewConfig {
    @Nullable
    Object title();

    int size();

    @NotNull
    String@Nullable[] layout();

    @NotNull
    InvType type();

    @Nullable
    Duration updateInterval();

    @Nullable
    Duration interactionDelay();

    @Nullable
    Consumer<ContextClick> onInteractionDelay();

    @NotNull
    @UnmodifiableView
    Map<ViewConfigOption<Object>, Object> options();

    @NotNull
    @UnmodifiableView
    Collection<ViewConfigModifier> modifiers();
}
