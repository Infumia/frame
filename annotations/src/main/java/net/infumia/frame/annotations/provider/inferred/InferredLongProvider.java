package net.infumia.frame.annotations.provider.inferred;

import net.infumia.frame.annotations.provider.Provider;
import net.infumia.frame.context.ContextBase;
import org.jetbrains.annotations.NotNull;

public final class InferredLongProvider implements Provider<ContextBase, Long> {

    private InferredLongProvider() {}

    @NotNull
    @Override
    public Long provide(@NotNull final ContextBase ctx) {
        return 0L;
    }
}
