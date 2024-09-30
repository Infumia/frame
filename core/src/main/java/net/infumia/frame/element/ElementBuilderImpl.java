package net.infumia.frame.element;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.state.State;
import org.jetbrains.annotations.NotNull;

public class ElementBuilderImpl implements ElementBuilderRich {

    ElementRich root;
    boolean cancelOnClick;
    boolean closeOnClick;
    boolean updateOnClick;
    Predicate<ContextRender> displayIf;
    Collection<State<?>> updateOnStateChange;
    Collection<State<?>> updateOnStateAccess;

    public ElementBuilderImpl(@NotNull final ElementRich element) {
        this.root = element.root();
        this.cancelOnClick = element.cancelOnClick();
        this.closeOnClick = element.closeOnClick();
        this.updateOnClick = element.updateOnClick();
        this.displayIf = element.displayIf();
        this.updateOnStateChange = element.updateOnStateChange();
        this.updateOnStateAccess = element.updateOnStateAccess();
    }

    public ElementBuilderImpl() {}

    @NotNull
    @Override
    public ElementBuilderRich root(@NotNull final ElementRich root) {
        this.root = root;
        return this;
    }

    @NotNull
    @Override
    public ElementRich build(@NotNull final ContextBase context) {
        return new ElementImpl(this, context);
    }

    @NotNull
    @Override
    public ElementBuilder cancelOnClick() {
        return this.cancelOnClick(true);
    }

    @NotNull
    @Override
    public ElementBuilder closeOnClick() {
        return this.closeOnClick(true);
    }

    @NotNull
    @Override
    public ElementBuilder updateOnClick() {
        return this.updateOnClick(true);
    }

    @NotNull
    @Override
    public ElementBuilder cancelOnClick(final boolean cancelOnClick) {
        this.cancelOnClick = cancelOnClick;
        return this;
    }

    @NotNull
    @Override
    public ElementBuilder closeOnClick(final boolean cancelOnClick) {
        this.closeOnClick = cancelOnClick;
        return this;
    }

    @NotNull
    @Override
    public ElementBuilder updateOnClick(final boolean updateOnClick) {
        this.updateOnClick = updateOnClick;
        return this;
    }

    @NotNull
    @Override
    public ElementBuilder updateOnStateChange(
        @NotNull final State<?> state,
        @NotNull final State<?> @NotNull... otherStates
    ) {
        if (this.updateOnStateChange == null) {
            this.updateOnStateChange = new HashSet<>();
        }
        this.updateOnStateChange.add(state);
        Collections.addAll(this.updateOnStateChange, otherStates);
        return this;
    }

    @NotNull
    @Override
    public ElementBuilder updateOnStateAccess(
        @NotNull final State<?> state,
        @NotNull final State<?> @NotNull... otherStates
    ) {
        if (this.updateOnStateAccess == null) {
            this.updateOnStateAccess = new HashSet<>();
        }
        this.updateOnStateAccess.add(state);
        Collections.addAll(this.updateOnStateAccess, otherStates);
        return this;
    }

    @NotNull
    @Override
    public ElementBuilder displayIf(@NotNull final Predicate<ContextRender> condition) {
        this.displayIf = condition;
        return this;
    }

    @NotNull
    @Override
    public ElementBuilder displayIf(@NotNull final BooleanSupplier condition) {
        return this.displayIf(__ -> condition.getAsBoolean());
    }

    @NotNull
    @Override
    public ElementBuilder hideIf(@NotNull final Predicate<ContextRender> condition) {
        return this.displayIf(ctx -> condition.negate().test(ctx));
    }

    @NotNull
    @Override
    public ElementBuilder hideIf(@NotNull final BooleanSupplier condition) {
        return this.hideIf(__ -> condition.getAsBoolean());
    }
}
