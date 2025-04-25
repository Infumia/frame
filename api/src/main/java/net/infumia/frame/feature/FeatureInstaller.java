package net.infumia.frame.feature;

import net.infumia.frame.Frame;
import org.jetbrains.annotations.NotNull;

public interface FeatureInstaller<T> {
    @NotNull
    Frame installFeature(Class<? extends Feature> feature);

    @NotNull
    Frame installFeature(Feature feature);
}
