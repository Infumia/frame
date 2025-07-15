package net.infumia.frame.annotations.service;

import net.infumia.frame.pipeline.context.PipelineContextFrame;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.typedkey.TypedKeyStorage;

public final class ServiceRegisterViewsAnnotations
    implements ConsumerService<PipelineContextFrame.RegisterViews> {

    public static final String KEY = "annotations";

    @Override
    public String key() {
        return ServiceRegisterViewsAnnotations.KEY;
    }

    @Override
    public void accept(
        final PipelineContextFrame.RegisterViews ctx,
        final TypedKeyStorage storage
    ) {}
}
