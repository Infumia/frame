package net.infumia.inv.core.element;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Predicate;
import net.infumia.inv.api.context.ContextBase;
import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.pipeline.executor.PipelineExecutorElement;
import net.infumia.inv.api.state.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ElementImpl implements ElementRich {

    private final String key;
    final ContextBase parent;
    final ElementRich root;
    final boolean cancelOnClick;
    final boolean updateOnClick;
    final boolean closeOnClick;
    final Predicate<ContextRender> displayIf;
    final Collection<State<?>> updateOnStateChange;
    final Collection<State<?>> updateOnStateAccess;
    private boolean visible = true;

    public ElementImpl(
        @NotNull final ElementBuilderImpl builder,
        @NotNull final ContextBase parent
    ) {
        this.key = UUID.randomUUID().toString();
        this.cancelOnClick = builder.cancelOnClick;
        this.updateOnClick = builder.updateOnClick;
        this.closeOnClick = builder.closeOnClick;
        this.displayIf = builder.displayIf;
        this.updateOnStateChange = builder.updateOnStateChange;
        this.updateOnStateAccess = builder.updateOnStateAccess;
        this.parent = parent;
        this.root = builder.root;
    }

    @NotNull
    @Override
    public ContextBase parent() {
        return this.parent;
    }

    @Nullable
    @Override
    public ElementRich root() {
        return this.root;
    }

    @Override
    public boolean visible() {
        return this.visible;
    }

    @Override
    public void visible(final boolean visible) {
        this.visible = visible;
    }

    @Override
    public boolean shouldRender(@NotNull final ContextRender context) {
        return this.displayIf == null || this.displayIf.test(context);
    }

    @Override
    public boolean containedWithin(final int slot) {
        return false;
    }

    @Override
    public boolean intersects(@NotNull final ElementRich element) {
        return false;
    }

    @NotNull
    @Override
    public ElementBuilderRich toBuilder() {
        return new ElementBuilderImpl(this);
    }

    @Override
    public boolean cancelOnClick() {
        return this.cancelOnClick;
    }

    @Override
    public boolean closeOnClick() {
        return this.closeOnClick;
    }

    @Override
    public boolean updateOnClick() {
        return this.updateOnClick;
    }

    @Nullable
    @Override
    public Predicate<ContextRender> displayIf() {
        return this.displayIf;
    }

    @Nullable
    @Override
    public Collection<State<?>> updateOnStateChange() {
        return this.updateOnStateChange;
    }

    @Nullable
    @Override
    public Collection<State<?>> updateOnStateAccess() {
        return this.updateOnStateAccess;
    }

    @Override
    public String key() {
        return this.key;
    }

    @NotNull
    @Override
    public PipelineExecutorElement pipelines() {
        throw new UnsupportedOperationException(
            "This element does not have a pipeline implementation!"
        );
    }
}
