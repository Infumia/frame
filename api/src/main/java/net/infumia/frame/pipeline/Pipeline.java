package net.infumia.frame.pipeline;

public interface Pipeline<B extends PipelineContext, R>
    extends PipelineBase<B, R, Pipeline<B, R>> {}
