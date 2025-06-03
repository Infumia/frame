package net.infumia.frame.element;

import java.time.Duration;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.context.element.ContextElementClick;
import net.infumia.frame.context.element.ContextElementRender;
import net.infumia.frame.pipeline.executor.PipelinesElement;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.state.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ElementImpl implements ElementRich {

    private final String key;
    protected final ContextBase parent;
    protected final Element root;
    protected final boolean cancelOnClick;
    protected final boolean updateOnClick;
    protected final boolean closeOnClick;
    protected final Duration interactionDelay;
    protected final Consumer<ContextElementClick> onInteractionDelay;
    protected final Function<ContextElementClick, String> interactionDelayKey;
    protected final Predicate<ContextElementRender> displayIf;
    protected final Collection<State<?>> updateOnStateChange;
    protected final Collection<State<?>> updateOnStateAccess;
    private boolean visible = true;

    public ElementImpl(
        @NotNull final ElementBuilderImpl builder,
        @NotNull final ContextBase parent
    ) {
        this.key = UUID.randomUUID().toString();
        this.parent = parent;
        this.root = builder.root;
        this.cancelOnClick = builder.cancelOnClick;
        this.updateOnClick = builder.updateOnClick;
        this.closeOnClick = builder.closeOnClick;
        this.interactionDelay = builder.interactionDelay;
        this.onInteractionDelay = builder.onInteractionDelay;
        this.interactionDelayKey = builder.interactionDelayKey;
        this.displayIf = builder.displayIf;
        this.updateOnStateChange = builder.updateOnStateChange;
        this.updateOnStateAccess = builder.updateOnStateAccess;
    }

    @Nullable
    @Override
    public Element root() {
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
    public boolean shouldRender(@NotNull final ContextElementRender context) {
        return this.displayIf == null || this.displayIf.test(context);
    }

    @Override
    public boolean containedWithin(final int slot) {
        return false;
    }

    @Override
    public boolean intersects(@NotNull final Element element) {
        return false;
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
    public Duration interactionDelay() {
        return this.interactionDelay;
    }

    @Nullable
    @Override
    public Consumer<ContextElementClick> onInteractionDelay() {
        return this.onInteractionDelay;
    }

    @Nullable
    @Override
    public Function<ContextElementClick, String> interactionDelayKey() {
        return this.interactionDelayKey;
    }

    @Nullable
    @Override
    public Predicate<ContextElementRender> displayIf() {
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

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> update() {
        throw new UnsupportedOperationException("This element cannot be updated!");
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> forceUpdate() {
        throw new UnsupportedOperationException("This element cannot be updated!");
    }

    @Override
    public String key() {
        return this.key;
    }

    @NotNull
    @Override
    public PipelinesElement pipelines() {
        throw new UnsupportedOperationException(
            "This element does not have a pipeline implementation!"
        );
    }
}
