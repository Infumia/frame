package net.infumia.frame.feature;

import net.infumia.frame.Frame;
import org.jetbrains.annotations.NotNull;

public interface FeatureInstaller {
    @NotNull
    Frame installFeature(@NotNull Class<? extends Feature> feature);

    @NotNull
    Frame installFeature(@NotNull Feature feature);
}
