package net.infumia.frame.service;

import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;

public interface Service<Context, Result> {
    @NotNull
    CompletableFuture<Result> handle(Context context);

    @NotNull
    String key();
}
