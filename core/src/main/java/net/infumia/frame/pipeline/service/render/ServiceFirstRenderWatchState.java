package net.infumia.frame.pipeline.service.render;

import java.util.Collection;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.element.Element;
import net.infumia.frame.element.ElementContainer;
import net.infumia.frame.element.ElementRich;
import net.infumia.frame.extension.CompletableFutureExtensions;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextRender;
import org.jetbrains.annotations.NotNull;

// TODO: portlek, Add more detailed messages for the errors below.
public final class ServiceFirstRenderWatchState
    implements PipelineServiceConsumer<PipelineContextRender.FirstRender> {

    public static final PipelineServiceConsumer<PipelineContextRender.FirstRender> INSTANCE =
        new ServiceFirstRenderWatchState();

    public static final String KEY = "watch-state";

    @Override
    public String key() {
        return ServiceFirstRenderWatchState.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextRender.FirstRender ctx) {
        final ContextRender context = ctx.context();
        for (final Element element : ctx.elements()) {
            ServiceFirstRenderWatchState.watch(context, (ElementRich) element);
        }
    }

    private static void watch(
        @NotNull final ContextRender context,
        @NotNull final ElementRich element
    ) {
        ServiceFirstRenderWatchState.updateOnStateAccess(context, element);
        ServiceFirstRenderWatchState.updateOnStateChange(context, element);
        if (element instanceof ElementContainer) {
            for (final Element child : ((ElementContainer) element).elements()) {
                ServiceFirstRenderWatchState.watch(context, (ElementRich) child);
            }
        }
    }

    private static void updateOnStateAccess(
        @NotNull final ContextRender context,
        @NotNull final ElementRich element
    ) {
        final Collection<net.infumia.frame.state.State<?>> states = element.updateOnStateAccess();
        if (states == null) {
            return;
        }
        for (final net.infumia.frame.state.State<?> state : states) {
            state.watchAccess(context, update ->
                CompletableFutureExtensions.logError(
                    element.pipelines().executeUpdate(context, false),
                    context.manager().logger(),
                    "An error occurred while updating element '%s' due to state '%s' access!",
                    element.key(),
                    state
                )
            );
        }
    }

    private static void updateOnStateChange(
        @NotNull final ContextRender context,
        @NotNull final ElementRich element
    ) {
        final Collection<net.infumia.frame.state.State<?>> states = element.updateOnStateChange();
        if (states == null) {
            return;
        }
        for (final net.infumia.frame.state.State<?> state : states) {
            state.watchUpdate(context, update ->
                CompletableFutureExtensions.logError(
                    element.pipelines().executeUpdate(context, false),
                    context.manager().logger(),
                    "An error occurred while updating element '%s' due to state '%s' change!",
                    element.key(),
                    state
                )
            );
        }
    }

    private ServiceFirstRenderWatchState() {}
}
