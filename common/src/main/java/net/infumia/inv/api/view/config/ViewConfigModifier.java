package net.infumia.inv.api.view.config;

import java.util.function.BiConsumer;
import net.infumia.inv.api.context.view.ContextOpen;

@FunctionalInterface
public interface ViewConfigModifier extends BiConsumer<ViewConfigBuilder, ContextOpen> {}
