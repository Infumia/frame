package net.infumia.inv.api.pipeline;

public interface Pipeline<B extends PipelineContext, R>
    extends PipelineBase<B, R, Pipeline<B, R>> {}
