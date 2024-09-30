package net.infumia.inv.core.pipeline.holder;

import io.leangen.geantyref.TypeToken;
import java.util.Collection;
import net.infumia.inv.api.context.ContextBase;
import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.pipeline.Pipeline;
import net.infumia.inv.api.pipeline.PipelineConsumer;
import net.infumia.inv.api.pipeline.PipelineService;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import net.infumia.inv.api.view.ViewContainer;
import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.core.pipeline.PipelineConsumerImpl;
import net.infumia.inv.core.pipeline.PipelineImpl;
import net.infumia.inv.core.pipeline.service.view.ServiceClickCancel;
import net.infumia.inv.core.pipeline.service.view.ServiceClickElement;
import net.infumia.inv.core.pipeline.service.view.ServiceClickInteractionDelay;
import net.infumia.inv.core.pipeline.service.view.ServiceClickLogging;
import net.infumia.inv.core.pipeline.service.view.ServiceClickOnClick;
import net.infumia.inv.core.pipeline.service.view.ServiceClose;
import net.infumia.inv.core.pipeline.service.view.ServiceCloseCancel;
import net.infumia.inv.core.pipeline.service.view.ServiceCloseLogging;
import net.infumia.inv.core.pipeline.service.view.ServiceCloseOnClose;
import net.infumia.inv.core.pipeline.service.view.ServiceCreateContainer;
import net.infumia.inv.core.pipeline.service.view.ServiceCreateContext;
import net.infumia.inv.core.pipeline.service.view.ServiceCreateRender;
import net.infumia.inv.core.pipeline.service.view.ServiceCreateViewers;
import net.infumia.inv.core.pipeline.service.view.ServiceInitLogging;
import net.infumia.inv.core.pipeline.service.view.ServiceInitOnInit;
import net.infumia.inv.core.pipeline.service.view.ServiceInitWaitUntil;
import net.infumia.inv.core.pipeline.service.view.ServiceLayoutResolution;
import net.infumia.inv.core.pipeline.service.view.ServiceLayoutResolutionLogging;
import net.infumia.inv.core.pipeline.service.view.ServiceModifyContainerLogging;
import net.infumia.inv.core.pipeline.service.view.ServiceOpenInitializeState;
import net.infumia.inv.core.pipeline.service.view.ServiceOpenLogging;
import net.infumia.inv.core.pipeline.service.view.ServiceOpenOnOpen;
import net.infumia.inv.core.pipeline.service.view.ServiceOpenPreviousView;
import net.infumia.inv.core.pipeline.service.view.ServiceOpenWaitUntil;
import net.infumia.inv.core.pipeline.service.view.ServiceProcessConfigModifier;
import net.infumia.inv.core.pipeline.service.view.ServiceProcessConfigModifierAddSizeModifier;
import net.infumia.inv.core.pipeline.service.view.ServiceProcessConfigModifierLogging;
import org.jetbrains.annotations.NotNull;

public final class PipelineHolderView {

    private final PipelineConsumer<PipelineContextView.Init> init;
    private final Pipeline<PipelineContextView.CreateViewers, Collection<Viewer>> createViewers;
    private final Pipeline<PipelineContextView.CreateContext, ContextBase> createContext;
    private final PipelineConsumer<PipelineContextView.Open> open;
    private final PipelineConsumer<
        PipelineContextView.ProcessConfigModifier
    > processConfigModifiers;
    private final Pipeline<PipelineContextView.CreateContainer, ViewContainer> createContainer;
    private final PipelineConsumer<PipelineContextView.ModifyContainer> modifyContainer;
    private final PipelineConsumer<PipelineContextView.LayoutResolution> layoutResolution;
    private final Pipeline<PipelineContextView.CreateRender, ContextRender> createRender;
    private final PipelineConsumer<PipelineContextView.Click> click;
    private final PipelineConsumer<PipelineContextView.Close> close;

    public static final PipelineHolderView BASE = new PipelineHolderView(
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextView.Init>>() {},
            ServiceInitLogging.INSTANCE
        )
            .register(ServiceInitWaitUntil.INSTANCE)
            .register(ServiceInitOnInit.INSTANCE),
        new PipelineImpl<>(
            new TypeToken<
                PipelineService<PipelineContextView.CreateViewers, Collection<Viewer>>
            >() {},
            ServiceCreateViewers.INSTANCE
        ),
        new PipelineImpl<>(
            new TypeToken<PipelineService<PipelineContextView.CreateContext, ContextBase>>() {},
            ServiceCreateContext.INSTANCE
        ),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextView.Open>>() {},
            ServiceOpenLogging.INSTANCE
        )
            .register(ServiceOpenWaitUntil.INSTANCE)
            .register(ServiceOpenOnOpen.INSTANCE)
            .register(ServiceOpenInitializeState.INSTANCE)
            .register(ServiceOpenPreviousView.INSTANCE),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextView.ProcessConfigModifier>>() {},
            ServiceProcessConfigModifierLogging.INSTANCE
        )
            .register(ServiceProcessConfigModifier.INSTANCE)
            .register(ServiceProcessConfigModifierAddSizeModifier.INSTANCE),
        new PipelineImpl<>(
            new TypeToken<PipelineService<PipelineContextView.CreateContainer, ViewContainer>>() {},
            ServiceCreateContainer.INSTANCE
        ),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextView.ModifyContainer>>() {},
            ServiceModifyContainerLogging.INSTANCE
        ),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextView.LayoutResolution>>() {},
            ServiceLayoutResolutionLogging.INSTANCE
        ).register(ServiceLayoutResolution.INSTANCE),
        new PipelineImpl<>(
            new TypeToken<PipelineService<PipelineContextView.CreateRender, ContextRender>>() {},
            ServiceCreateRender.INSTANCE
        ),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextView.Click>>() {},
            ServiceClickLogging.INSTANCE
        )
            .register(ServiceClickElement.INSTANCE)
            .register(ServiceClickOnClick.INSTANCE)
            .register(ServiceClickInteractionDelay.INSTANCE)
            .register(ServiceClickCancel.INSTANCE),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextView.Close>>() {},
            ServiceCloseLogging.INSTANCE
        )
            .register(ServiceClose.INSTANCE)
            .register(ServiceCloseCancel.INSTANCE)
            .register(ServiceCloseOnClose.INSTANCE)
    );

    @NotNull
    public PipelineConsumer<PipelineContextView.Init> init() {
        return this.init;
    }

    @NotNull
    public Pipeline<PipelineContextView.CreateViewers, Collection<Viewer>> createViewers() {
        return this.createViewers;
    }

    @NotNull
    public Pipeline<PipelineContextView.CreateContext, ContextBase> createContext() {
        return this.createContext;
    }

    @NotNull
    public PipelineConsumer<PipelineContextView.Open> open() {
        return this.open;
    }

    @NotNull
    public PipelineConsumer<PipelineContextView.ProcessConfigModifier> processConfigModifiers() {
        return this.processConfigModifiers;
    }

    @NotNull
    public Pipeline<PipelineContextView.CreateContainer, ViewContainer> createContainer() {
        return this.createContainer;
    }

    @NotNull
    public PipelineConsumer<PipelineContextView.ModifyContainer> modifyContainer() {
        return this.modifyContainer;
    }

    @NotNull
    public PipelineConsumer<PipelineContextView.LayoutResolution> layoutResolution() {
        return this.layoutResolution;
    }

    @NotNull
    public Pipeline<PipelineContextView.CreateRender, ContextRender> createRender() {
        return this.createRender;
    }

    @NotNull
    public PipelineConsumer<PipelineContextView.Click> click() {
        return this.click;
    }

    @NotNull
    public PipelineConsumer<PipelineContextView.Close> close() {
        return this.close;
    }

    @NotNull
    public PipelineHolderView createNew() {
        return new PipelineHolderView(
            this.init,
            this.createViewers,
            this.createContext,
            this.open,
            this.processConfigModifiers,
            this.createContainer,
            this.modifyContainer,
            this.layoutResolution,
            this.createRender,
            this.click,
            this.close
        );
    }

    public PipelineHolderView(
        @NotNull final PipelineConsumer<PipelineContextView.Init> init,
        @NotNull final Pipeline<
            PipelineContextView.CreateViewers,
            Collection<Viewer>
        > createViewers,
        @NotNull final Pipeline<PipelineContextView.CreateContext, ContextBase> createContext,
        @NotNull final PipelineConsumer<PipelineContextView.Open> open,
        @NotNull final PipelineConsumer<
            PipelineContextView.ProcessConfigModifier
        > processConfigModifiers,
        @NotNull final Pipeline<PipelineContextView.CreateContainer, ViewContainer> createContainer,
        @NotNull final PipelineConsumer<PipelineContextView.ModifyContainer> modifyContainer,
        @NotNull final PipelineConsumer<PipelineContextView.LayoutResolution> layoutResolution,
        @NotNull final Pipeline<PipelineContextView.CreateRender, ContextRender> createRender,
        @NotNull final PipelineConsumer<PipelineContextView.Click> click,
        @NotNull final PipelineConsumer<PipelineContextView.Close> close
    ) {
        this.init = init;
        this.createViewers = createViewers;
        this.createContext = createContext;
        this.open = open;
        this.processConfigModifiers = processConfigModifiers;
        this.createContainer = createContainer;
        this.modifyContainer = modifyContainer;
        this.layoutResolution = layoutResolution;
        this.createRender = createRender;
        this.click = click;
        this.close = close;
    }
}
