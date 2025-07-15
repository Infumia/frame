package net.infumia.frame.config;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Consumer;
import net.infumia.frame.Preconditions;
import net.infumia.frame.Reflection;
import net.infumia.frame.context.view.ContextClick;
import net.infumia.frame.type.InvType;
import net.infumia.frame.view.config.ViewConfigBuilder;
import net.infumia.frame.view.config.ViewConfigModifier;
import net.infumia.frame.view.config.option.ViewConfigOption;
import net.infumia.frame.view.config.option.ViewConfigOptions;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

final class ViewConfigBuilderImpl implements ViewConfigBuilderRich {

    private static final boolean COMPONENT_SUPPORT = Reflection.hasClass(
        "net.kyori.adventure.text.Component"
    );

    final Map<ViewConfigOption<Object>, Object> options;
    final Collection<ViewConfigModifier> modifiers;
    Object title;
    int size = -1;
    InvType type = InvType.CHEST;
    String[] layout;
    Duration updateInterval;
    Duration interactionDelay;
    Consumer<ContextClick> onInteractionDelay;

    ViewConfigBuilderImpl(@NotNull final ViewConfigImpl config) {
        this.options = new HashMap<>(config.options);
        this.modifiers = new HashSet<>(config.modifiers);
        this.title = config.title;
        this.size = config.size;
        this.type = config.type;
        this.layout = config.layout;
        this.updateInterval = config.updateInterval;
        this.interactionDelay = config.interactionDelay;
        this.onInteractionDelay = config.onInteractionDelay;
    }

    ViewConfigBuilderImpl() {
        this.options = new HashMap<>();
        this.modifiers = new HashSet<>();
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
        return Collections.unmodifiableMap(this.options);
    }

    @NotNull
    @Override
    @UnmodifiableView
    public Collection<ViewConfigModifier> modifiers() {
        return Collections.unmodifiableCollection(this.modifiers);
    }

    @NotNull
    @Override
    public ViewConfigBuilderRich title(@NotNull final Object title) {
        ViewConfigBuilderImpl.checkTitleType(title);
        this.title = title;
        return this;
    }

    @NotNull
    @Override
    public ViewConfigBuilder layout(@NotNull final String@Nullable [] layout) {
        this.layout = layout;
        return this;
    }

    @NotNull
    @Override
    public ViewConfigBuilder size(final int size) {
        this.size = size;
        return this;
    }

    @NotNull
    @Override
    public ViewConfigBuilder type(@NotNull final InvType type) {
        this.type = type;
        return this;
    }

    @NotNull
    @Override
    public ViewConfigBuilder updateInterval(@NotNull final Duration updateInterval) {
        this.updateInterval = updateInterval;
        return this;
    }

    @NotNull
    @Override
    public ViewConfigBuilder interactionDelay(@NotNull final Duration interactionDelay) {
        this.interactionDelay = interactionDelay;
        return this;
    }

    @NotNull
    @Override
    public ViewConfigBuilderRich cancelOnClick() {
        return this.addOption(ViewConfigOptions.CANCEL_ON_CLICK);
    }

    @NotNull
    @Override
    public ViewConfigBuilderRich cancelOnPickup() {
        return this.addOption(ViewConfigOptions.CANCEL_ON_PICKUP);
    }

    @NotNull
    @Override
    public ViewConfigBuilderRich cancelOnDrop() {
        return this.addOption(ViewConfigOptions.CANCEL_ON_DROP);
    }

    @NotNull
    @Override
    public ViewConfigBuilderRich cancelOnDrag() {
        return this.addOption(ViewConfigOptions.CANCEL_ON_DRAG);
    }

    @NotNull
    @Override
    public ViewConfigBuilderRich cancelDefaults() {
        this.cancelOnClick().cancelOnPickup().cancelOnDrop().cancelOnDrag();
        return this;
    }

    @NotNull
    @Override
    public <T> ViewConfigBuilderRich addOption(@NotNull final ViewConfigOption<T> option) {
        return this.addOption(
            option,
            Preconditions.argumentNotNull(
                option.defaultValue(),
                "Option '%s' does not have a default value! Please use #addOption(ViewConfigOption<T>, T) method instead!"
            )
        );
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public <T> ViewConfigBuilderRich addOption(
        @NotNull final ViewConfigOption<T> option,
        @NotNull final T value
    ) {
        this.options.put((ViewConfigOption<Object>) option, value);
        return this;
    }

    @NotNull
    @Override
    public ViewConfigBuilderRich addModifier(@NotNull final ViewConfigModifier modifier) {
        this.modifiers.add(modifier);
        return this;
    }

    @NotNull
    @Override
    public ViewConfigBuilderRich addModifier(@NotNull final ViewConfigModifier... modifiers) {
        Collections.addAll(this.modifiers, modifiers);
        return this;
    }

    @NotNull
    @Override
    public ViewConfigRich build() {
        return new ViewConfigImpl(this);
    }

    private static void checkTitleType(@NotNull final Object title) {
        boolean check = title instanceof String;
        if (ViewConfigBuilderImpl.COMPONENT_SUPPORT) {
            check |= title instanceof Component;
        }
        Preconditions.argument(check, "Title must be only String or Component!");
    }
}
