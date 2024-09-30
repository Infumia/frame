package net.infumia.frame.context.view;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.FrameRich;
import net.infumia.frame.config.ViewConfigBuilderRich;
import net.infumia.frame.context.ContextImpl;
import net.infumia.frame.state.StateRegistry;
import net.infumia.frame.typedkey.TypedKeyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ContextInitImpl extends ContextImpl implements ContextInitRich {

    private final ViewConfigBuilderRich configBuilder;
    private CompletableFuture<?> waitUntil;

    public ContextInitImpl(
        @NotNull final FrameRich manager,
        @NotNull final TypedKeyStorage instances,
        @NotNull final StateRegistry stateRegistry,
        @NotNull final ViewConfigBuilderRich configBuilder
    ) {
        super(manager, instances, stateRegistry);
        this.configBuilder = configBuilder;
    }

    @NotNull
    @Override
    public ViewConfigBuilderRich configBuilder() {
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
