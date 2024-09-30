package net.infumia.inv.core.pipeline.holder;

import io.leangen.geantyref.TypeToken;
import net.infumia.inv.api.pipeline.PipelineConsumer;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextElement;
import net.infumia.inv.core.pipeline.PipelineConsumerImpl;
import net.infumia.inv.core.pipeline.service.element.ServiceClear;
import net.infumia.inv.core.pipeline.service.element.ServiceClearLogging;
import net.infumia.inv.core.pipeline.service.element.ServiceClick;
import net.infumia.inv.core.pipeline.service.element.ServiceClickCancelOnClick;
import net.infumia.inv.core.pipeline.service.element.ServiceClickCloseOnClick;
import net.infumia.inv.core.pipeline.service.element.ServiceClickLogging;
import net.infumia.inv.core.pipeline.service.element.ServiceClickUpdateOnClick;
import net.infumia.inv.core.pipeline.service.element.ServiceRender;
import net.infumia.inv.core.pipeline.service.element.ServiceRenderLogging;
import net.infumia.inv.core.pipeline.service.element.ServiceUpdate;
import net.infumia.inv.core.pipeline.service.element.ServiceUpdateLogging;
import org.jetbrains.annotations.NotNull;

public final class PipelineHolderElement {

    private final PipelineConsumer<PipelineContextElement.Render> render;
    private final PipelineConsumer<PipelineContextElement.Clear> clear;
    private final PipelineConsumer<PipelineContextElement.Click> click;
    private final PipelineConsumer<PipelineContextElement.Update> update;

    public static final PipelineHolderElement BASE = new PipelineHolderElement(
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextElement.Render>>() {},
            ServiceRenderLogging.INSTANCE
        ).register(ServiceRender.INSTANCE),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextElement.Clear>>() {},
            ServiceClearLogging.INSTANCE
        ).register(ServiceClear.INSTANCE),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextElement.Click>>() {},
            ServiceClickLogging.INSTANCE
        )
            .register(ServiceClickCloseOnClick.INSTANCE)
            .register(ServiceClickUpdateOnClick.INSTANCE)
            .register(ServiceClick.INSTANCE)
            .register(ServiceClickCancelOnClick.INSTANCE),
        new PipelineConsumerImpl<>(
            new TypeToken<PipelineServiceConsumer<PipelineContextElement.Update>>() {},
            ServiceUpdateLogging.INSTANCE
        ).register(ServiceUpdate.INSTANCE)
    );

    @NotNull
    public PipelineConsumer<PipelineContextElement.Render> render() {
        return this.render;
    }

    @NotNull
    public PipelineConsumer<PipelineContextElement.Clear> clear() {
        return this.clear;
    }

    @NotNull
    public PipelineConsumer<PipelineContextElement.Click> click() {
        return this.click;
    }

    @NotNull
    public PipelineConsumer<PipelineContextElement.Update> update() {
        return this.update;
    }

    @NotNull
    public PipelineHolderElement createNew() {
        return new PipelineHolderElement(this.render, this.clear, this.click, this.update);
    }

    public PipelineHolderElement(
        @NotNull final PipelineConsumer<PipelineContextElement.Render> render,
        @NotNull final PipelineConsumer<PipelineContextElement.Clear> clear,
        @NotNull final PipelineConsumer<PipelineContextElement.Click> click,
        @NotNull final PipelineConsumer<PipelineContextElement.Update> update
    ) {
        this.render = render;
        this.clear = clear;
        this.click = click;
        this.update = update;
    }
}
