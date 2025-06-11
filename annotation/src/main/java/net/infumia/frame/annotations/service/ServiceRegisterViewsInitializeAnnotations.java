package net.infumia.frame.annotations.service;

import net.infumia.frame.pipeline.context.PipelineContextFrame;
import net.infumia.frame.service.ConsumerService;

public final class ServiceRegisterViewsInitializeAnnotations
    implements ConsumerService<PipelineContextFrame.RegisterViews> {

    public static final String KEY = "initialize-annotations";

    @Override
    public String key() {
        return ServiceRegisterViewsInitializeAnnotations.KEY;
    }

    @Override
    public void accept(final PipelineContextFrame.RegisterViews ctx) {}
}
