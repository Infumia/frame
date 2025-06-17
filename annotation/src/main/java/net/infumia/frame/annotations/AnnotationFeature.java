package net.infumia.frame.annotations;

import net.infumia.frame.Frame;
import net.infumia.frame.annotations.service.ServiceRegisterViewsAnnotations;
import net.infumia.frame.feature.Feature;
import net.infumia.frame.pipeline.service.frame.ServiceRegisterViews;
import net.infumia.frame.service.Implementation;
import org.jetbrains.annotations.NotNull;

public final class AnnotationFeature implements Feature {

    @NotNull
    @Override
    public String name() {
        return "annotations";
    }

    @Override
    public void onInstall(@NotNull final Frame frame) {
        frame.pipelines().applyRegisterViews(
            Implementation.registerAfter(ServiceRegisterViews.KEY, new ServiceRegisterViewsAnnotations())
        );
    }

    @Override
    public void onUninstall(@NotNull final Frame frame) {}
}
