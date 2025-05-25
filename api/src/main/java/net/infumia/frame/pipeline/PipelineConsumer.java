package net.infumia.frame.pipeline;

import net.infumia.frame.service.ConsumerService;

public interface PipelineConsumer<Context>
    extends PipelineBase<Context, ConsumerService.State, PipelineConsumer<Context>> {}
