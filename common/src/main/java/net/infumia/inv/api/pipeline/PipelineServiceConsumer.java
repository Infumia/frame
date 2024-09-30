package net.infumia.inv.api.pipeline;

import net.infumia.inv.api.service.ConsumerService;

public interface PipelineServiceConsumer<C extends PipelineContext>
    extends ConsumerService<C>, PipelineService<C, ConsumerService.State> {}
