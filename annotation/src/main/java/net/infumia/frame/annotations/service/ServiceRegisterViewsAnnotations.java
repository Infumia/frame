package net.infumia.frame.annotations.service;

import java.util.Collection;
import java.util.function.BiConsumer;
import net.infumia.frame.pipeline.context.PipelineContextFrame;
import net.infumia.frame.view.View;

public final class ServiceRegisterViewsAnnotations
    implements BiConsumer<PipelineContextFrame.RegisterViews, Collection<View>> {

    @Override
    public void accept(
        final PipelineContextFrame.RegisterViews registerViews,
        final Collection<View> views
    ) {}
}
