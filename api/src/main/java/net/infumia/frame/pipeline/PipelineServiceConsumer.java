package net.infumia.frame.pipeline;

import net.infumia.frame.service.ConsumerService;

public interface PipelineServiceConsumer<Context>
    extends ConsumerService<Context>, PipelineService<Context, ConsumerService.State> {}
