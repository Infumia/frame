package net.infumia.frame.pipeline;

import net.infumia.frame.service.ConsumerService;

public interface PipelineConsumer<B extends PipelineContext>
    extends PipelineBase<B, ConsumerService.State, PipelineConsumer<B>> {}
