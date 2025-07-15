package net.infumia.frame.pipeline.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.context.view.ContextClick;
import net.infumia.frame.context.view.ContextClose;
import net.infumia.frame.context.view.ContextOpen;
import net.infumia.frame.slot.LayoutSlot;
import net.infumia.frame.slot.LayoutSlotImpl;
import net.infumia.frame.typedkey.TypedKeyStorageImmutable;
import net.infumia.frame.view.View;
import net.infumia.frame.view.ViewContainer;
import net.infumia.frame.view.config.ViewConfig;
import net.infumia.frame.viewer.Viewer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface PipelineContextViews {
    final class Init implements PipelineContextView.Init {

        private final View view;

        public Init(@NotNull final View view) {
            this.view = view;
        }

        @NotNull
        @Override
        public View view() {
            return this.view;
        }
    }

    final class CreateViewers implements PipelineContextView.CreateViewers {

        private final View view;
        private final Collection<Player> viewers;

        public CreateViewers(@NotNull final View view, @NotNull final Collection<Player> viewers) {
            this.view = view;
            this.viewers = viewers;
        }

        @NotNull
        @Override
        public View view() {
            return this.view;
        }

        @NotNull
        @Override
        public Collection<Player> viewers() {
            return this.viewers;
        }
    }

    final class CreateContext implements PipelineContextView.CreateContext {

        private final View view;
        private final Collection<Viewer> viewers;
        private final TypedKeyStorageImmutable initialData;

        public CreateContext(
            @NotNull final View view,
            @NotNull final Collection<Viewer> viewers,
            @NotNull final TypedKeyStorageImmutable initialData
        ) {
            this.view = view;
            this.viewers = viewers;
            this.initialData = initialData;
        }

        @NotNull
        @Override
        public View view() {
            return this.view;
        }

        @NotNull
        @Override
        public Collection<Viewer> viewers() {
            return this.viewers;
        }

        @NotNull
        @Override
        public TypedKeyStorageImmutable initialData() {
            return this.initialData;
        }
    }

    final class StartTransition implements PipelineContextView.StartTransition {

        private final ContextBase context;
        private final Collection<Viewer> viewers;

        public StartTransition(
            @NotNull final ContextBase context,
            final Collection<Viewer> viewers
        ) {
            this.context = context;
            this.viewers = viewers;
        }

        @NotNull
        @Override
        public ContextBase context() {
            return this.context;
        }

        @NotNull
        @Override
        public Collection<Viewer> viewers() {
            return this.viewers;
        }
    }

    final class Open implements PipelineContextView.Open {

        private final ContextOpen context;
        private boolean cancelled;

        public Open(@NotNull final ContextOpen context) {
            this.context = context;
        }

        @NotNull
        @Override
        public ContextOpen context() {
            return this.context;
        }

        @Override
        public boolean cancelled() {
            return this.cancelled;
        }

        @Override
        public void cancelled(final boolean cancelled) {
            this.cancelled = cancelled;
        }
    }

    final class ProcessConfigModifier implements PipelineContextView.ProcessConfigModifier {

        private final ContextOpen context;

        public ProcessConfigModifier(@NotNull final ContextOpen context) {
            this.context = context;
        }

        @NotNull
        @Override
        public ContextOpen context() {
            return this.context;
        }
    }

    final class CreateContainer implements PipelineContextView.CreateContainer {

        private final ContextBase context;
        private final ViewConfig config;

        public CreateContainer(
            @NotNull final ContextBase context,
            @NotNull final ViewConfig config
        ) {
            this.context = context;
            this.config = config;
        }

        @NotNull
        @Override
        public ContextBase context() {
            return this.context;
        }

        @NotNull
        @Override
        public ViewConfig config() {
            return this.config;
        }
    }

    final class ModifyContainer implements PipelineContextView.ModifyContainer {

        private final ContextBase context;
        private final ViewConfig config;
        private ViewContainer container;

        public ModifyContainer(
            @NotNull final ContextBase context,
            @NotNull final ViewConfig config,
            @NotNull final ViewContainer container
        ) {
            this.context = context;
            this.config = config;
            this.container = container;
        }

        @NotNull
        @Override
        public ContextBase context() {
            return this.context;
        }

        @NotNull
        @Override
        public ViewConfig config() {
            return this.config;
        }

        @NotNull
        @Override
        public ViewContainer container() {
            return this.container;
        }

        @Override
        public void modifyContainer(@NotNull final ViewContainer newContainer) {
            this.container = newContainer;
        }
    }

    final class LayoutResolution implements PipelineContextView.LayoutResolution {

        private final Collection<LayoutSlot> layouts = new ArrayList<>();
        private final ContextBase context;
        private final ViewConfig config;
        private final ViewContainer container;

        public LayoutResolution(
            @NotNull final ContextBase context,
            @NotNull final ViewConfig config,
            @NotNull final ViewContainer container
        ) {
            this.context = context;
            this.config = config;
            this.container = container;
        }

        @NotNull
        @Override
        public ContextBase context() {
            return this.context;
        }

        @NotNull
        @Override
        public ViewConfig config() {
            return this.config;
        }

        @NotNull
        @Override
        public ViewContainer container() {
            return this.container;
        }

        @NotNull
        @Override
        public Collection<LayoutSlot> layouts() {
            return Collections.unmodifiableCollection(this.layouts);
        }

        @Override
        public void addLayout(final char character, @NotNull final Collection<Integer> indexes) {
            this.layouts.add(
                    new LayoutSlotImpl(
                        character,
                        indexes
                            .stream()
                            .mapToInt(value -> value)
                            .toArray()
                    )
                );
        }
    }

    final class CreateRender implements PipelineContextView.CreateRender {

        private final ContextBase context;
        private final ViewConfig config;
        private final ViewContainer container;
        private final Collection<LayoutSlot> layouts;

        public CreateRender(
            @NotNull final ContextBase context,
            @NotNull final ViewConfig config,
            @NotNull final ViewContainer container,
            @NotNull final Collection<LayoutSlot> layouts
        ) {
            this.context = context;
            this.config = config;
            this.container = container;
            this.layouts = layouts;
        }

        @NotNull
        @Override
        public ContextBase context() {
            return this.context;
        }

        @NotNull
        @Override
        public ViewConfig config() {
            return this.config;
        }

        @NotNull
        @Override
        public ViewContainer container() {
            return this.container;
        }

        @NotNull
        @Override
        public Collection<LayoutSlot> layouts() {
            return this.layouts;
        }
    }

    final class Click implements PipelineContextView.Click {

        private final ContextClick context;
        private boolean cancelled;

        public Click(@NotNull final ContextClick context) {
            this.context = context;
        }

        @NotNull
        @Override
        public ContextClick context() {
            return this.context;
        }

        @Override
        public boolean cancelled() {
            return this.cancelled;
        }

        @Override
        public void cancelled(final boolean cancelled) {
            this.cancelled = cancelled;
        }
    }

    final class Close implements PipelineContextView.Close {

        private final ContextClose context;
        private boolean cancelled;

        public Close(@NotNull final ContextClose context) {
            this.context = context;
        }

        @NotNull
        @Override
        public ContextClose context() {
            return this.context;
        }

        @Override
        public boolean cancelled() {
            return this.cancelled;
        }

        @Override
        public void cancelled(final boolean cancelled) {
            this.cancelled = cancelled;
        }
    }
}
