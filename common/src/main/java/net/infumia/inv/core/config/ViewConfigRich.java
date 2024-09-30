package net.infumia.inv.core.config;

import net.infumia.inv.api.view.config.ViewConfig;
import net.infumia.inv.api.view.config.option.ViewConfigOptionController;
import org.jetbrains.annotations.NotNull;

public interface ViewConfigRich extends ViewConfig, ViewConfigOptionController {
    @NotNull
    @Override
    Object title();

    @NotNull
    ViewConfigBuilderRich toBuilder();
}
