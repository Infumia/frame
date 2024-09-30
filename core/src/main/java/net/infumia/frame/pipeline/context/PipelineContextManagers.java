package net.infumia.frame.pipeline.context;

import java.util.Collection;
import net.infumia.frame.Frame;
import net.infumia.frame.FrameRich;
import net.infumia.frame.view.View;
import org.jetbrains.annotations.NotNull;

public interface PipelineContextManagers {
    final class ViewCreated implements PipelineContextManager.ViewCreated {

        private final FrameRich manager;
        private final Collection<Class<?>> registeredViews;

        public ViewCreated(
            @NotNull final FrameRich manager,
            @NotNull final Collection<Class<?>> registeredViews
        ) {
            this.manager = manager;
            this.registeredViews = registeredViews;
        }

        @NotNull
        @Override
        public Collection<Class<?>> registeredViews() {
            return this.registeredViews;
        }

        @NotNull
        @Override
        public Frame manager() {
            return this.manager;
        }
    }

    final class ViewRegistered implements PipelineContextManager.ViewRegistered {

        private final FrameRich manager;
        private final Collection<Object> registeredViews;

        public ViewRegistered(
            @NotNull final FrameRich manager,
            @NotNull final Collection<Object> registeredViews
        ) {
            this.manager = manager;
            this.registeredViews = registeredViews;
        }

        @NotNull
        @Override
        public FrameRich manager() {
            return this.manager;
        }

        @NotNull
        @Override
        public Collection<Object> registeredViews() {
            return this.registeredViews;
        }
    }

    final class ListenerRegistered implements PipelineContextManager.ListenerRegistered {

        private final Frame manager;

        public ListenerRegistered(@NotNull final Frame manager) {
            this.manager = manager;
        }

        @NotNull
        @Override
        public Frame manager() {
            return this.manager;
        }
    }

    final class ViewUnregistered implements PipelineContextManager.ViewUnregistered {

        private final FrameRich manager;
        private final Collection<View> unregisteredViews;

        public ViewUnregistered(
            @NotNull final FrameRich manager,
            @NotNull final Collection<View> unregisteredViews
        ) {
            this.manager = manager;
            this.unregisteredViews = unregisteredViews;
        }

        @NotNull
        @Override
        public FrameRich manager() {
            return this.manager;
        }

        @NotNull
        @Override
        public Collection<View> unregisteredViews() {
            return this.unregisteredViews;
        }
    }
}
