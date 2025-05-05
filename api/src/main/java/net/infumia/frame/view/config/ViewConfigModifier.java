package net.infumia.frame.view.config;

import java.util.function.BiConsumer;
import net.infumia.frame.context.view.ContextOpen;

@FunctionalInterface
public interface ViewConfigModifier extends BiConsumer<ViewConfigBuilder, ContextOpen> {}
