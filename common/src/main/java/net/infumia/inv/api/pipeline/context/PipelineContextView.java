package net.infumia.inv.api.pipeline.context;

import java.util.Collection;
import java.util.Map;
import net.infumia.inv.api.context.ContextBase;
import net.infumia.inv.api.context.view.ContextClick;
import net.infumia.inv.api.context.view.ContextClose;
import net.infumia.inv.api.context.view.ContextOpen;
import net.infumia.inv.api.pipeline.PipelineContext;
import net.infumia.inv.api.service.Cancellable;
import net.infumia.inv.api.slot.LayoutSlot;
import net.infumia.inv.api.typedkey.TypedKeyStorageImmutable;
import net.infumia.inv.api.view.View;
import net.infumia.inv.api.view.ViewContainer;
import net.infumia.inv.api.view.config.ViewConfig;
import net.infumia.inv.api.viewer.Viewer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface PipelineContextView extends PipelineContext {
    interface Init extends PipelineContextView {
        @NotNull
        View view();
    }

    interface CreateViewers extends PipelineContextView {
        @NotNull
        View view();

        @NotNull
        Collection<Player> viewers();
    }

    interface CreateContext extends PipelineContextView {
        @NotNull
        View view();

        @NotNull
        Collection<Viewer> viewers();

        @NotNull
        TypedKeyStorageImmutable initialData();
    }

    interface Transition extends PipelineContextView {
        @NotNull
        ContextBase context();

        @NotNull
        Collection<Viewer> viewers();
    }

    interface Open extends PipelineContextView, Cancellable {
        @NotNull
        ContextOpen context();
    }

    interface ProcessConfigModifier extends PipelineContextView {
        @NotNull
        ContextOpen context();
    }

    interface CreateContainer extends PipelineContextView {
        @NotNull
        ContextBase context();

        @NotNull
        ViewConfig config();
    }

    interface ModifyContainer extends PipelineContextView {
        @NotNull
        ContextBase context();

        @NotNull
        ViewConfig config();

        @NotNull
        ViewContainer container();

        void modifyContainer(@NotNull ViewContainer newContainer);
    }

    interface LayoutResolution extends PipelineContextView {
        @NotNull
        ContextBase context();

        @NotNull
        ViewConfig config();

        @NotNull
        ViewContainer container();

        @NotNull
        Map<Character, LayoutSlot> layouts();

        void addLayout(char character, @NotNull Collection<Integer> indexes);
    }

    interface CreateRender extends PipelineContextView {
        @NotNull
        ContextBase context();

        @NotNull
        ViewConfig config();

        @NotNull
        ViewContainer container();

        @NotNull
        Map<Character, LayoutSlot> layouts();
    }

    interface Click extends PipelineContextView, Cancellable {
        @NotNull
        ContextClick context();
    }

    interface Close extends PipelineContextView, Cancellable {
        @NotNull
        ContextClose context();
    }
}
