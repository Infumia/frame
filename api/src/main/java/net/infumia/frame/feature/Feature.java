package net.infumia.frame.feature;

import net.infumia.frame.Frame;
import org.jetbrains.annotations.NotNull;

public interface Feature {
    @NotNull
    String name();

    void onInstall(@NotNull Frame frame);

    void onUninstall(@NotNull Frame frame);
}
