package net.infumia.inv.api.context.view;

import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.context.ContextBase;
import net.infumia.inv.api.service.Cancellable;
import net.infumia.inv.api.view.config.ViewConfig;
import net.infumia.inv.api.view.config.ViewConfigBuilder;
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
