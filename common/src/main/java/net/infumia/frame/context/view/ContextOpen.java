package net.infumia.frame.context.view;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.service.Cancellable;
import net.infumia.frame.view.config.ViewConfig;
import net.infumia.frame.view.config.ViewConfigBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ContextOpen extends ContextBase, Cancellable {
    void waitUntil(@NotNull CompletableFuture<?> future);

    @Nullable
    CompletableFuture<?> waitUntil();

    @NotNull
    ViewConfigBuilder modifyConfig();

    @NotNull
    ViewConfig buildFinalConfig();
}
