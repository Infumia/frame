package net.infumia.inv.core.pipeline.executor;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.context.ContextBase;
import net.infumia.inv.api.context.view.ContextInit;
import net.infumia.inv.api.context.view.ContextOpen;
import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import net.infumia.inv.api.pipeline.executor.PipelineExecutorView;
import net.infumia.inv.api.service.ConsumerService;
import net.infumia.inv.api.service.Implementation;
import net.infumia.inv.api.slot.LayoutSlot;
import net.infumia.inv.api.typedkey.TypedKeyStorageImmutable;
import net.infumia.inv.api.util.Pair;
import net.infumia.inv.api.view.ViewContainer;
import net.infumia.inv.api.view.config.ViewConfig;
import net.infumia.inv.api.viewer.ContextualViewer;
import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.core.config.ViewConfigRich;
import net.infumia.inv.core.context.ContextBaseRich;
import net.infumia.inv.core.context.view.ContextClickImpl;
import net.infumia.inv.core.context.view.ContextCloseImpl;
import net.infumia.inv.core.context.view.ContextInitRich;
import net.infumia.inv.core.context.view.ContextOpenImpl;
import net.infumia.inv.core.pipeline.context.PipelineContextViews;
import net.infumia.inv.core.pipeline.holder.PipelineHolderView;
import net.infumia.inv.core.view.ViewContainerRich;
import net.infumia.inv.core.view.ViewRich;
import net.infumia.inv.core.viewer.ContextualViewerRich;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public final class PipelineExecutorViewImpl implements PipelineExecutorView {

    private final PipelineHolderView pipelines = PipelineHolderView.BASE.createNew();
    private final ViewRich view;

    public PipelineExecutorViewImpl(@NotNull final ViewRich view) {
        this.view = view;
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeInit(
        @NotNull final ContextInit context
    ) {
        return this.pipelines.init()
            .completeWith(new PipelineContextViews.Init(this.view, (ContextInitRich) context));
    }

    @NotNull
    @Override
    public CompletableFuture<Collection<Viewer>> executeCreateViewers(
        @NotNull final Collection<Player> viewers
    ) {
        return this.pipelines.createViewers()
            .completeWith(new PipelineContextViews.CreateViewers(this.view, viewers));
    }

    @NotNull
    @Override
    public CompletableFuture<ContextBase> executeCreateContext(
        @NotNull final Collection<Viewer> viewers,
        @NotNull final TypedKeyStorageImmutable initialData
    ) {
        return this.pipelines.createContext()
            .completeWith(new PipelineContextViews.CreateContext(this.view, viewers, initialData));
    }

    @NotNull
    @Override
    public CompletableFuture<Pair<ConsumerService.State, ContextOpen>> executeOpen(
        @NotNull final ContextBase context
    ) {
        final ContextOpenImpl open = new ContextOpenImpl((ContextBaseRich) context);
        return this.pipelines.open()
            .completeWith(new PipelineContextViews.Open(open))
            .thenApply(state -> Pair.of(state, open));
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeProcessConfigModifiers(
        @NotNull final ContextOpen context
    ) {
        return this.pipelines.processConfigModifiers()
            .completeWith(new PipelineContextViews.ProcessConfigModifier(context));
    }

    @NotNull
    @Override
    public CompletableFuture<ViewContainer> executeCreateContainer(
        @NotNull final ContextBase context,
        @NotNull final ViewConfig config
    ) {
        return this.pipelines.createContainer()
            .completeWith(
                new PipelineContextViews.CreateContainer(
                    (ContextBaseRich) context,
                    (ViewConfigRich) config
                )
            );
    }

    @NotNull
    @Override
    public CompletableFuture<
        Pair<ConsumerService.State, PipelineContextView.ModifyContainer>
    > executeModifyContainer(
        @NotNull final ContextBase context,
        @NotNull final ViewConfig config,
        @NotNull final ViewContainer container
    ) {
        final PipelineContextViews.ModifyContainer ctx = new PipelineContextViews.ModifyContainer(
            (ContextBaseRich) context,
            (ViewConfigRich) config,
            (ViewContainerRich) container
        );
        return this.pipelines.modifyContainer()
            .completeWith(ctx)
            .thenApply(state -> Pair.of(state, ctx));
    }

    @NotNull
    @Override
    public CompletableFuture<
        Pair<ConsumerService.State, Map<Character, LayoutSlot>>
    > executeLayoutResolution(
        @NotNull final ContextBase context,
        @NotNull final ViewConfig config,
        @NotNull final ViewContainer container
    ) {
        final PipelineContextView.LayoutResolution layoutResolution =
            new PipelineContextViews.LayoutResolution(
                (ContextBaseRich) context,
                (ViewConfigRich) config,
                (ViewContainerRich) container
            );
        return this.pipelines.layoutResolution()
            .completeWith(layoutResolution)
            .thenApply(state -> Pair.of(state, layoutResolution.layouts()));
    }

    @NotNull
    @Override
    public CompletableFuture<ContextRender> executeCreateRender(
        @NotNull final ContextBase context,
        @NotNull final ViewConfig config,
        @NotNull final ViewContainer container,
        @NotNull final Map<Character, LayoutSlot> layouts
    ) {
        return this.pipelines.createRender()
            .completeWith(
                new PipelineContextViews.CreateRender(
                    (ContextBaseRich) context,
                    (ViewConfigRich) config,
                    (ViewContainerRich) container,
                    layouts
                )
            );
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeClick(
        @NotNull final ContextualViewer clicker,
        @NotNull final InventoryClickEvent event
    ) {
        return this.pipelines.click()
            .completeWith(
                new PipelineContextViews.Click(
                    new ContextClickImpl((ContextualViewerRich) clicker, event)
                )
            );
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> executeClose(
        @NotNull final ContextualViewer viewer,
        final boolean forced
    ) {
        return this.pipelines.close()
            .completeWith(
                new PipelineContextViews.Close(
                    new ContextCloseImpl((ContextualViewerRich) viewer, forced)
                )
            );
    }

    @Override
    public void applyInit(
        @NotNull final Implementation<
            PipelineContextView.Init,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.init().apply(implementation);
    }

    @Override
    public void applyCreateViewers(
        @NotNull final Implementation<
            PipelineContextView.CreateViewers,
            Collection<Viewer>
        > implementation
    ) {
        this.pipelines.createViewers().apply(implementation);
    }

    @Override
    public void applyCreateContext(
        @NotNull final Implementation<PipelineContextView.CreateContext, ContextBase> implementation
    ) {
        this.pipelines.createContext().apply(implementation);
    }

    @Override
    public void applyOpen(
        @NotNull final Implementation<
            PipelineContextView.Open,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.open().apply(implementation);
    }

    @Override
    public void applyProcessConfigModifiers(
        @NotNull final Implementation<
            PipelineContextView.ProcessConfigModifier,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.processConfigModifiers().apply(implementation);
    }

    @Override
    public void applyCreateContainer(
        @NotNull final Implementation<
            PipelineContextView.CreateContainer,
            ViewContainer
        > implementation
    ) {
        this.pipelines.createContainer().apply(implementation);
    }

    @Override
    public void applyModifyContainer(
        @NotNull final Implementation<
            PipelineContextView.ModifyContainer,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.modifyContainer().apply(implementation);
    }

    @Override
    public void applyLayoutResolution(
        @NotNull final Implementation<
            PipelineContextView.LayoutResolution,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.layoutResolution().apply(implementation);
    }

    @Override
    public void applyCreateRender(
        @NotNull final Implementation<
            PipelineContextView.CreateRender,
            ContextRender
        > implementation
    ) {
        this.pipelines.createRender().apply(implementation);
    }

    @Override
    public void applyClick(
        @NotNull final Implementation<
            PipelineContextView.Click,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.click().apply(implementation);
    }

    @Override
    public void applyClose(
        @NotNull final Implementation<
            PipelineContextView.Close,
            ConsumerService.State
        > implementation
    ) {
        this.pipelines.close().apply(implementation);
    }
}
