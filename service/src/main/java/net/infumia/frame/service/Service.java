package net.infumia.frame.service;

import java.util.concurrent.CompletableFuture;

public interface Service<Context, Result> {
    CompletableFuture<Result> handle(Context context);

    String key();
}
