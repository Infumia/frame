package net.infumia.frame.annotations.provider.inferred;

import net.infumia.frame.annotations.provider.Provider;
import net.infumia.frame.context.ContextBase;
import org.jetbrains.annotations.NotNull;

public final class InferredIntegerProvider implements Provider<ContextBase, Integer> {

    private InferredIntegerProvider() {}

    @NotNull
    @Override
    public Integer provide(@NotNull final ContextBase ctx) {
        return 0;
    }
}
