package net.infumia.inv.api.pipeline;

import net.infumia.inv.api.service.Service;

public interface PipelineService<C extends PipelineContext, R> extends Service<C, R> {}
