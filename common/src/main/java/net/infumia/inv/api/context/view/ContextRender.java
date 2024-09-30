package net.infumia.inv.api.context.view;

import java.util.Map;
import net.infumia.inv.api.context.ContextBase;
import net.infumia.inv.api.element.ElementContainer;
import net.infumia.inv.api.element.ElementItemBuilderFactory;
import net.infumia.inv.api.pipeline.Pipelined;
import net.infumia.inv.api.pipeline.executor.PipelineExecutorRender;
import net.infumia.inv.api.pipeline.executor.PipelineExecutorViewer;
import net.infumia.inv.api.slot.LayoutSlot;
import net.infumia.inv.api.view.ViewContainer;
import net.infumia.inv.api.view.config.ViewConfig;
import org.jetbrains.annotations.NotNull;

public interface ContextRender
    extends
        ContextBase,
        ElementItemBuilderFactory,
        ElementContainer,
        Pipelined<PipelineExecutorRender> {
    @NotNull
    ViewContainer container();

    @NotNull
    ViewConfig config();

    @NotNull
    Map<Character, LayoutSlot> layouts();

    void back();

    boolean canBack();

    void closeForEveryone();

    void closeForViewer();

    void closeForEveryone(boolean forced);

    void closeForViewer(boolean forced);

    @NotNull
    PipelineExecutorViewer pipelinesViewer();
}
