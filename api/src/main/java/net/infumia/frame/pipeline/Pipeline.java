package net.infumia.frame.pipeline;

public interface Pipeline<Context, Result>
    extends PipelineBase<Context, Result, Pipeline<Context, Result>> {}
