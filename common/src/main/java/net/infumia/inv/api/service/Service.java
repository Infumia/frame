package net.infumia.inv.api.service;

import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.util.Keyed;
import org.jetbrains.annotations.NotNull;

public interface Service<Context, Result> extends Keyed<String> {
    @NotNull
    CompletableFuture<Result> handle(Context context);
}
