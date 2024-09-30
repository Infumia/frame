package net.infumia.frame.element;

import net.infumia.frame.context.ContextBase;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.pipeline.Pipelined;
import net.infumia.frame.pipeline.executor.PipelineExecutorElement;
import net.infumia.frame.util.Keyed;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ElementRich extends Element, Pipelined<PipelineExecutorElement>, Keyed<String> {
    @NotNull
    ContextBase parent();

    @Nullable
    ElementRich root();

    boolean visible();

    void visible(boolean visible);

    boolean shouldRender(@NotNull ContextRender context);

    boolean containedWithin(int slot);

    boolean intersects(@NotNull ElementRich element);

    @NotNull
    ElementBuilderRich toBuilder();
}
