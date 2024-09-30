package net.infumia.frame.context.view;

import java.util.concurrent.CompletableFuture;
import net.infumia.frame.config.ViewConfigBuilderRich;
import net.infumia.frame.config.ViewConfigRich;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.context.ContextBaseImpl;
import net.infumia.frame.view.config.ViewConfig;
import net.infumia.frame.view.config.ViewConfigBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ContextOpenImpl extends ContextBaseImpl implements ContextOpen {

    private ViewConfigBuilder modifiedConfig;
    private CompletableFuture<?> waitUntil;
    private boolean cancelled;

    public ContextOpenImpl(@NotNull final ContextBase context) {
        super(context);
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

    @NotNull
    @Override
    public ViewConfigBuilder modifyConfig() {
        if (this.modifiedConfig == null) {
            this.modifiedConfig = ((ViewConfigRich) this.initialConfig()).toBuilder();
        }
        return this.modifiedConfig;
    }

    @NotNull
    @Override
    public ViewConfig buildFinalConfig() {
        if (this.modifiedConfig == null) {
            return this.initialConfig();
        }
        return ((ViewConfigBuilderRich) this.modifiedConfig).build();
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
