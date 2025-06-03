package net.infumia.frame.element;

import net.infumia.frame.Keyed;
import net.infumia.frame.context.element.ContextElementRender;
import net.infumia.frame.pipeline.Pipelined;
import net.infumia.frame.pipeline.executor.PipelinesElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ElementRich extends Element, Pipelined<PipelinesElement>, Keyed<String> {
    @Nullable
    Element root();

    boolean visible();

    void visible(boolean visible);

    boolean shouldRender(@NotNull ContextElementRender context);

    boolean containedWithin(int slot);

    boolean intersects(@NotNull Element element);
}
