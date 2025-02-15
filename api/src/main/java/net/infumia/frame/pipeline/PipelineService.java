package net.infumia.frame.pipeline;

import net.infumia.frame.service.Service;

public interface PipelineService<C extends PipelineContext, R> extends Service<C, R> {}
