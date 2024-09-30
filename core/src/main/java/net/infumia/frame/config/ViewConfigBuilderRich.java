package net.infumia.frame.config;

import net.infumia.frame.view.config.ViewConfigBuilder;
import org.jetbrains.annotations.NotNull;

public interface ViewConfigBuilderRich extends ViewConfigBuilder {
    @NotNull
    static ViewConfigBuilderRich create() {
        return new ViewConfigBuilderImpl();
    }

    @NotNull
    ViewConfigRich build();
}
