package net.infumia.frame.annotations;

import net.infumia.frame.Frame;
import net.infumia.frame.annotations.decorator.ViewDecoratorFactoryImpl;
import net.infumia.frame.feature.Feature;
import org.jetbrains.annotations.NotNull;

public final class AnnotationFeature implements Feature {

    private final ViewDecoratorFactoryImpl decoratorFactory;

    public AnnotationFeature() {
        this.decoratorFactory = new ViewDecoratorFactoryImpl();
    }

    @NotNull
    public ViewDecoratorFactory decoratorFactory() {
        return this.decoratorFactory;
    }

    @NotNull
    @Override
    public String name() {
        return "annotations";
    }

    @Override
    public void onInstall(@NotNull final Frame frame) {}

    @Override
    public void onUninstall(@NotNull final Frame frame) {
        // TODO: Unregister services and clear decorators
    }
}
