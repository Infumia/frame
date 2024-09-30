package net.infumia.frame.service;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.util.Keyed;
import org.jetbrains.annotations.NotNull;

public interface Service<Context, Result> extends Keyed<String> {
    @NotNull
    CompletableFuture<Result> handle(Context context);
}
