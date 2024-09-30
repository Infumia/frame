package net.infumia.inv.core.pipeline.context;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.infumia.inv.api.context.view.ContextClick;
import net.infumia.inv.api.context.view.ContextClose;
import net.infumia.inv.api.context.view.ContextOpen;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import net.infumia.inv.api.slot.LayoutSlot;
import net.infumia.inv.api.typedkey.TypedKeyStorageImmutable;
import net.infumia.inv.api.view.View;
import net.infumia.inv.api.view.ViewContainer;
import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.core.config.ViewConfigRich;
import net.infumia.inv.core.context.ContextBaseRich;
import net.infumia.inv.core.context.view.ContextInitRich;
import net.infumia.inv.core.slot.LayoutSlotImpl;
import net.infumia.inv.core.view.ViewContainerRich;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface PipelineContextViews {
    final class Init implements PipelineContextView.Init {

        private final View view;
        private final ContextInitRich context;

        public Init(@NotNull final View view, @NotNull final ContextInitRich context) {
            this.view = view;
            this.context = context;
        }

        @NotNull
        @Override
        public View view() {
            return this.view;
        }

        @NotNull
        public ContextInitRich context() {
            return this.context;
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

    final class Transition implements PipelineContextView.Transition {

        private final ContextBaseRich context;
        private final Collection<Viewer> viewers;

        public Transition(
            @NotNull final ContextBaseRich context,
            final Collection<Viewer> viewers
        ) {
            this.context = context;
            this.viewers = viewers;
        }

        @NotNull
        @Override
        public ContextBaseRich context() {
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

        private final ContextBaseRich context;
        private final ViewConfigRich config;

        public CreateContainer(
            @NotNull final ContextBaseRich context,
            @NotNull final ViewConfigRich config
        ) {
            this.context = context;
            this.config = config;
        }

        @NotNull
        @Override
        public ContextBaseRich context() {
            return this.context;
        }

        @NotNull
        @Override
        public ViewConfigRich config() {
            return this.config;
        }
    }

    final class ModifyContainer implements PipelineContextView.ModifyContainer {

        private final ContextBaseRich context;
        private final ViewConfigRich config;
        private ViewContainerRich container;

        public ModifyContainer(
            @NotNull final ContextBaseRich context,
            @NotNull final ViewConfigRich config,
            @NotNull final ViewContainerRich container
        ) {
            this.context = context;
            this.config = config;
            this.container = container;
        }

        @NotNull
        @Override
        public ContextBaseRich context() {
            return this.context;
        }

        @NotNull
        @Override
        public ViewConfigRich config() {
            return this.config;
        }

        @NotNull
        @Override
        public ViewContainerRich container() {
            return this.container;
        }

        @Override
        public void modifyContainer(@NotNull final ViewContainer newContainer) {
            this.container = (ViewContainerRich) newContainer;
        }
    }

    final class LayoutResolution implements PipelineContextView.LayoutResolution {

        private final Map<Character, LayoutSlot> layouts = new ConcurrentHashMap<>();
        private final ContextBaseRich context;
        private final ViewConfigRich config;
        private final ViewContainerRich container;

        public LayoutResolution(
            @NotNull final ContextBaseRich context,
            @NotNull final ViewConfigRich config,
            @NotNull final ViewContainerRich container
        ) {
            this.context = context;
            this.config = config;
            this.container = container;
        }

        @NotNull
        @Override
        public ContextBaseRich context() {
            return this.context;
        }

        @NotNull
        @Override
        public ViewConfigRich config() {
            return this.config;
        }

        @NotNull
        @Override
        public ViewContainerRich container() {
            return this.container;
        }

        @NotNull
        @Override
        public Map<Character, LayoutSlot> layouts() {
            return Collections.unmodifiableMap(this.layouts);
        }

        @Override
        public void addLayout(final char character, @NotNull final Collection<Integer> indexes) {
            this.layouts.computeIfAbsent(character, __ ->
                    new LayoutSlotImpl(
                        character,
                        indexes.stream().mapToInt(value -> value).toArray()
                    )
                );
        }
    }

    final class CreateRender implements PipelineContextView.CreateRender {

        private final ContextBaseRich context;
        private final ViewConfigRich config;
        private final ViewContainerRich container;
        private final Map<Character, LayoutSlot> layouts;

        public CreateRender(
            @NotNull final ContextBaseRich context,
            @NotNull final ViewConfigRich config,
            @NotNull final ViewContainerRich container,
            @NotNull final Map<Character, LayoutSlot> layouts
        ) {
            this.context = context;
            this.config = config;
            this.container = container;
            this.layouts = layouts;
        }

        @NotNull
        @Override
        public ContextBaseRich context() {
            return this.context;
        }

        @NotNull
        @Override
        public ViewConfigRich config() {
            return this.config;
        }

        @NotNull
        @Override
        public ViewContainerRich container() {
            return this.container;
        }

        @NotNull
        @Override
        public Map<Character, LayoutSlot> layouts() {
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
