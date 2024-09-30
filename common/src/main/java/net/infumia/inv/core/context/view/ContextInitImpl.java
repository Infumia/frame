package net.infumia.inv.core.context.view;

import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.typedkey.TypedKeyStorage;
import net.infumia.inv.core.InventoryManagerRich;
import net.infumia.inv.core.config.ViewConfigBuilderRich;
import net.infumia.inv.core.context.ContextImpl;
import net.infumia.inv.core.state.StateRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ContextInitImpl extends ContextImpl implements ContextInitRich {

    private final ViewConfigBuilderRich configBuilder;
    private CompletableFuture<?> waitUntil;

    public ContextInitImpl(
        @NotNull final InventoryManagerRich manager,
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
