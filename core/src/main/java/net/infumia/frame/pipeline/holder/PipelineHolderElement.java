package net.infumia.frame.pipeline.holder;

import io.leangen.geantyref.TypeToken;
import net.infumia.frame.pipeline.PipelineConsumer;
import net.infumia.frame.pipeline.PipelineConsumerImpl;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextElement;
import net.infumia.frame.pipeline.service.element.ServiceClear;
import net.infumia.frame.pipeline.service.element.ServiceClearLogging;
import net.infumia.frame.pipeline.service.element.ServiceClick;
import net.infumia.frame.pipeline.service.element.ServiceClickCancelOnClick;
import net.infumia.frame.pipeline.service.element.ServiceClickCloseOnClick;
import net.infumia.frame.pipeline.service.element.ServiceClickLogging;
import net.infumia.frame.pipeline.service.element.ServiceClickUpdateOnClick;
import net.infumia.frame.pipeline.service.element.ServiceRender;
import net.infumia.frame.pipeline.service.element.ServiceRenderLogging;
import net.infumia.frame.pipeline.service.element.ServiceUpdate;
import net.infumia.frame.pipeline.service.element.ServiceUpdateLogging;
import net.infumia.frame.util.Cloned;
import org.jetbrains.annotations.NotNull;

public final class PipelineHolderElement implements Cloned<PipelineHolderElement> {

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
    @Override
    public PipelineHolderElement cloned() {
        return new PipelineHolderElement(
            this.render.cloned(),
            this.clear.cloned(),
            this.click.cloned(),
            this.update.cloned()
        );
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
