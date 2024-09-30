package net.infumia.frame.pipeline.context;

import java.util.Collection;
import java.util.function.Consumer;
import net.infumia.frame.Frame;
import net.infumia.frame.typedkey.TypedKeyStorageImmutableBuilder;
import net.infumia.frame.view.View;
import org.jetbrains.annotations.NotNull;

public interface PipelineContextFrames {
    final class ViewCreated implements PipelineContextFrame.ViewCreated {

        private final Frame frame;
        private final Collection<Class<?>> registeredViews;

        public ViewCreated(
            @NotNull final Frame frame,
            @NotNull final Collection<Class<?>> registeredViews
        ) {
            this.frame = frame;
            this.registeredViews = registeredViews;
        }

        @NotNull
        @Override
        public Collection<Class<?>> registeredViews() {
            return this.registeredViews;
        }

        @NotNull
        @Override
        public Frame frame() {
            return this.frame;
        }
    }

    final class ViewRegistered implements PipelineContextFrame.ViewRegistered {

        private final Frame frame;
        private final Collection<Object> registeredViews;
        private final Consumer<TypedKeyStorageImmutableBuilder> storageConfigurer;

        public ViewRegistered(
            @NotNull final Frame frame,
            @NotNull final Collection<Object> registeredViews,
            @NotNull final Consumer<TypedKeyStorageImmutableBuilder> storageConfigurer
        ) {
            this.frame = frame;
            this.registeredViews = registeredViews;
            this.storageConfigurer = storageConfigurer;
        }

        @NotNull
        @Override
        public Frame frame() {
            return this.frame;
        }

        @NotNull
        @Override
        public Collection<Object> registeredViews() {
            return this.registeredViews;
        }

        @NotNull
        @Override
        public Consumer<TypedKeyStorageImmutableBuilder> instanceConfigurer() {
            return this.storageConfigurer;
        }
    }

    final class ListenerRegistered implements PipelineContextFrame.ListenerRegistered {

        private final Frame frame;

        public ListenerRegistered(@NotNull final Frame frame) {
            this.frame = frame;
        }

        @NotNull
        @Override
        public Frame frame() {
            return this.frame;
        }
    }

    final class ViewUnregistered implements PipelineContextFrame.ViewUnregistered {

        private final Frame frame;
        private final Collection<View> unregisteredViews;

        public ViewUnregistered(
            @NotNull final Frame frame,
            @NotNull final Collection<View> unregisteredViews
        ) {
            this.frame = frame;
            this.unregisteredViews = unregisteredViews;
        }

        @NotNull
        @Override
        public Frame frame() {
            return this.frame;
        }

        @NotNull
        @Override
        public Collection<View> unregisteredViews() {
            return this.unregisteredViews;
        }
    }
}
