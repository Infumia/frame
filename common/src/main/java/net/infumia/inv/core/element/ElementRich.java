package net.infumia.inv.core.element;

import net.infumia.inv.api.context.ContextBase;
import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.element.Element;
import net.infumia.inv.api.pipeline.Pipelined;
import net.infumia.inv.api.pipeline.executor.PipelineExecutorElement;
import net.infumia.inv.api.util.Keyed;
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
