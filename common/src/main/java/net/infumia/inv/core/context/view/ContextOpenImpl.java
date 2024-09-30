package net.infumia.inv.core.context.view;

import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.context.view.ContextOpen;
import net.infumia.inv.api.view.config.ViewConfig;
import net.infumia.inv.api.view.config.ViewConfigBuilder;
import net.infumia.inv.core.config.ViewConfigBuilderRich;
import net.infumia.inv.core.context.ContextBaseImpl;
import net.infumia.inv.core.context.ContextBaseRich;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ContextOpenImpl extends ContextBaseImpl implements ContextBaseRich, ContextOpen {

    private ViewConfigBuilderRich modifiedConfig;
    private CompletableFuture<?> waitUntil;
    private boolean cancelled;

    public ContextOpenImpl(@NotNull final ContextBaseRich context) {
        super(context);
    }

    @Override
    public void waitUntil(final @Nullable CompletableFuture<?> waitUntil) {
        this.waitUntil = waitUntil;
    }

    @Nullable
    @Override
    public CompletableFuture<?> waitUntil() {
        return this.waitUntil;
    }

    @NotNull
    @Override
    public ViewConfigBuilder modifyConfig() {
        if (this.modifiedConfig == null) {
            this.modifiedConfig = this.initialConfig().toBuilder();
        }
        return this.modifiedConfig;
    }

    @NotNull
    @Override
    public ViewConfig buildFinalConfig() {
        if (this.modifiedConfig == null) {
            return this.initialConfig();
        }
        return this.modifiedConfig.build();
    }

    @Override
    public boolean cancelled() {
        return this.cancelled;
    }

    @Override
    public void cancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}
