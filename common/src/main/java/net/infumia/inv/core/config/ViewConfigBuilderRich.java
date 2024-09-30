package net.infumia.inv.core.config;

import net.infumia.inv.api.view.config.ViewConfigBuilder;
import org.jetbrains.annotations.NotNull;

public interface ViewConfigBuilderRich extends ViewConfigBuilder {
    @NotNull
    static ViewConfigBuilderRich create() {
        return new ViewConfigBuilderImpl();
    }

    @NotNull
    ViewConfigRich build();
}
