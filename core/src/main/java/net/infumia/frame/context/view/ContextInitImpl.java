package net.infumia.frame.context.view;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.Frame;
import net.infumia.frame.context.ContextImpl;
import net.infumia.frame.context.ContextRich;
import net.infumia.frame.state.StateRegistry;
import net.infumia.frame.typedkey.TypedKeyStorage;
import net.infumia.frame.view.config.ViewConfigBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ContextInitImpl extends ContextImpl implements ContextRich, ContextInit {

    private final ViewConfigBuilder configBuilder;
    private CompletableFuture<?> waitUntil;

    public ContextInitImpl(
        @NotNull final Frame manager,
        @NotNull final TypedKeyStorage instances,
        @NotNull final StateRegistry stateRegistry,
        @NotNull final ViewConfigBuilder configBuilder
    ) {
        super(manager, instances, stateRegistry);
        this.configBuilder = configBuilder;
    }

    @NotNull
    @Override
    public ViewConfigBuilder configBuilder() {
        return this.configBuilder;
    }

    @Override
    public void waitUntil(@Nullable final CompletableFuture<?> waitUntil) {
        this.waitUntil = waitUntil;
    }

    @Nullable
    @Override
    public CompletableFuture<?> waitUntil() {
        return this.waitUntil;
    }
}
