package net.infumia.frame.element;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.context.element.ContextElementClick;
import net.infumia.frame.context.element.ContextElementRender;
import net.infumia.frame.state.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ElementBuilderImpl<Self extends ElementBuilderImpl<Self>>
    implements ElementBuilderRich {

    Element root;
    boolean cancelOnClick;
    boolean closeOnClick;
    boolean updateOnClick;
    Duration interactionDelay;
    Consumer<ContextElementClick> onInteractionDelay;
    Function<ContextElementClick, String> interactionDelayKey;
    Predicate<ContextElementRender> displayIf;
    Collection<State<?>> updateOnStateChange;
    Collection<State<?>> updateOnStateAccess;

    public ElementBuilderImpl(@NotNull final Element element) {
        this.root = ((ElementRich) element).root();
        this.cancelOnClick = element.cancelOnClick();
        this.closeOnClick = element.closeOnClick();
        this.updateOnClick = element.updateOnClick();
        this.interactionDelay = element.interactionDelay();
        this.onInteractionDelay = element.onInteractionDelay();
        this.displayIf = element.displayIf();
        this.updateOnStateChange = element.updateOnStateChange();
        this.updateOnStateAccess = element.updateOnStateAccess();
    }

    public ElementBuilderImpl() {}

    @NotNull
    @Override
    public Self root(@NotNull final Element root) {
        this.root = root;
        return this.self();
    }

    @NotNull
    @Override
    public Element build(@NotNull final ContextBase context) {
        return new ElementImpl(this, context);
    }

    @NotNull
    @Override
    public Self cancelOnClick() {
        return this.cancelOnClick(true);
    }

    @NotNull
    @Override
    public Self closeOnClick() {
        return this.closeOnClick(true);
    }

    @NotNull
    @Override
    public Self updateOnClick() {
        return this.updateOnClick(true);
    }

    @NotNull
    @Override
    public Self cancelOnClick(final boolean cancelOnClick) {
        this.cancelOnClick = cancelOnClick;
        return this.self();
    }

    @NotNull
    @Override
    public Self closeOnClick(final boolean cancelOnClick) {
        this.closeOnClick = cancelOnClick;
        return this.self();
    }

    @NotNull
    @Override
    public Self updateOnClick(final boolean updateOnClick) {
        this.updateOnClick = updateOnClick;
        return this.self();
    }

    @NotNull
    @Override
    public Self interactionDelay(@Nullable final Duration interactionDelay) {
        this.interactionDelay = interactionDelay;
        return this.self();
    }

    @NotNull
    @Override
    public Self onInteractionDelay(
        @NotNull final Consumer<ContextElementClick> onInteractionDelay
    ) {
        this.onInteractionDelay = onInteractionDelay;
        return this.self();
    }

    @NotNull
    @Override
    public Self interactionDelayKey(
        @NotNull final Function<ContextElementClick, String> interactionDelayKey
    ) {
        this.interactionDelayKey = interactionDelayKey;
        return this.self();
    }

    @NotNull
    @Override
    public Self updateOnStateChange(
        @NotNull final State<?> state,
        @NotNull final State<?> @NotNull... otherStates
    ) {
        if (this.updateOnStateChange == null) {
            this.updateOnStateChange = new HashSet<>();
        }
        this.updateOnStateChange.add(state);
        Collections.addAll(this.updateOnStateChange, otherStates);
        return this.self();
    }

    @NotNull
    @Override
    public Self updateOnStateAccess(
        @NotNull final State<?> state,
        @NotNull final State<?> @NotNull... otherStates
    ) {
        if (this.updateOnStateAccess == null) {
            this.updateOnStateAccess = new HashSet<>();
        }
        this.updateOnStateAccess.add(state);
        Collections.addAll(this.updateOnStateAccess, otherStates);
        return this.self();
    }

    @NotNull
    @Override
    public Self displayIf(@NotNull final Predicate<ContextElementRender> condition) {
        this.displayIf = condition;
        return this.self();
    }

    @NotNull
    @Override
    public Self displayIf(@NotNull final BooleanSupplier condition) {
        return this.displayIf(__ -> condition.getAsBoolean());
    }

    @NotNull
    @Override
    public Self hideIf(@NotNull final Predicate<ContextElementRender> condition) {
        return this.displayIf(ctx -> condition.negate().test(ctx));
    }

    @NotNull
    @Override
    public Self hideIf(@NotNull final BooleanSupplier condition) {
        return this.hideIf(__ -> condition.getAsBoolean());
    }

    @NotNull
    @SuppressWarnings("unchecked")
    private Self self() {
        return (Self) this;
    }
}
