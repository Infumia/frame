package net.infumia.frame.context.view;

import java.util.Collection;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.element.ElementContainer;
import net.infumia.frame.element.ElementItemBuilderFactory;
import net.infumia.frame.pipeline.Pipelined;
import net.infumia.frame.pipeline.executor.PipelineExecutorRender;
import net.infumia.frame.pipeline.executor.PipelineExecutorViewer;
import net.infumia.frame.slot.LayoutSlot;
import net.infumia.frame.view.ViewContainer;
import net.infumia.frame.view.config.ViewConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

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
    @UnmodifiableView
    Collection<LayoutSlot> layouts();

    void back();

    boolean canBack();

    void closeForEveryone();

    void closeForViewer();

    void closeForEveryone(boolean forced);

    void closeForViewer(boolean forced);

    @NotNull
    PipelineExecutorViewer pipelinesViewer();
}
