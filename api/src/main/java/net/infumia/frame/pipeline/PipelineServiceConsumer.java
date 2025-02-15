package net.infumia.frame.pipeline;

import net.infumia.frame.service.ConsumerService;

public interface PipelineServiceConsumer<C extends PipelineContext>
    extends ConsumerService<C>, PipelineService<C, ConsumerService.State> {}
