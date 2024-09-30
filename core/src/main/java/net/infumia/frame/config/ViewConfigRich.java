package net.infumia.frame.config;

import net.infumia.frame.view.config.ViewConfig;
import net.infumia.frame.view.config.option.ViewConfigOptionController;
import org.jetbrains.annotations.NotNull;

public interface ViewConfigRich extends ViewConfig, ViewConfigOptionController {
    @NotNull
    ViewConfigBuilderRich toBuilder();
}
