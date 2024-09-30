package net.infumia.frame.context.view;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.Context;
import net.infumia.frame.view.config.ViewConfigBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ContextInit extends Context {
    @NotNull
    ViewConfigBuilder configBuilder();

    void waitUntil(@Nullable CompletableFuture<?> waitUntil);

    @Nullable
    CompletableFuture<?> waitUntil();
}
