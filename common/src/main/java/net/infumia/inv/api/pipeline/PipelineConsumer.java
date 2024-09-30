package net.infumia.inv.api.pipeline;

import net.infumia.inv.api.service.ConsumerService;

public interface PipelineConsumer<B extends PipelineContext>
    extends PipelineBase<B, ConsumerService.State, PipelineConsumer<B>> {}
