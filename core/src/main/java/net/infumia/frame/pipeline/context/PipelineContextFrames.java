package net.infumia.frame.pipeline.context;

import java.util.Collection;
import java.util.function.Consumer;
import net.infumia.frame.Frame;
import net.infumia.frame.typedkey.TypedKeyStorageImmutableBuilder;
import net.infumia.frame.view.View;
import org.jetbrains.annotations.NotNull;

public interface PipelineContextFrames {
    final class CreateViews implements PipelineContextFrame.CreateViews {

        private final Frame frame;
        private final Collection<Class<?>> rawViews;

        public CreateViews(
            @NotNull final Frame frame,
            @NotNull final Collection<Class<?>> rawViews
        ) {
            this.frame = frame;
            this.rawViews = rawViews;
        }

        @NotNull
        @Override
        public Collection<Class<?>> rawViews() {
            return this.rawViews;
        }

        @NotNull
        @Override
        public Frame frame() {
            return this.frame;
        }
    }

    final class RegisterViews implements PipelineContextFrame.RegisterViews {

        private final Frame frame;
        private final Collection<Object> views;
        private final Consumer<TypedKeyStorageImmutableBuilder> instanceConfigurer;

        public RegisterViews(
            @NotNull final Frame frame,
            @NotNull final Collection<Object> views,
            @NotNull final Consumer<TypedKeyStorageImmutableBuilder> instanceConfigurer
        ) {
            this.frame = frame;
            this.views = views;
            this.instanceConfigurer = instanceConfigurer;
        }

        @NotNull
        @Override
        public Frame frame() {
            return this.frame;
        }

        @NotNull
        @Override
        public Collection<Object> views() {
            return this.views;
        }

        @NotNull
        @Override
        public Consumer<TypedKeyStorageImmutableBuilder> instanceConfigurer() {
            return this.instanceConfigurer;
        }
    }

    final class RegisterListeners implements PipelineContextFrame.RegisterListeners {

        private final Frame frame;

        public RegisterListeners(@NotNull final Frame frame) {
            this.frame = frame;
        }

        @NotNull
        @Override
        public Frame frame() {
            return this.frame;
        }
    }

    final class UnregisterViews implements PipelineContextFrame.UnregisterViews {

        private final Frame frame;
        private final Collection<View> views;

        public UnregisterViews(@NotNull final Frame frame, @NotNull final Collection<View> views) {
            this.frame = frame;
            this.views = views;
        }

        @NotNull
        @Override
        public Frame frame() {
            return this.frame;
        }

        @NotNull
        @Override
        public Collection<View> views() {
            return this.views;
        }
    }
}
