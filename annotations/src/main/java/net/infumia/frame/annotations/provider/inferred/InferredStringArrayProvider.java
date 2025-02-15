package net.infumia.frame.annotations.provider.inferred;

import net.infumia.frame.annotations.provider.Provider;
import net.infumia.frame.context.ContextBase;
import org.jetbrains.annotations.NotNull;

public final class InferredStringArrayProvider implements Provider<ContextBase, String[]> {

    private InferredStringArrayProvider() {}

    @NotNull
    @Override
    public String[] provide(@NotNull final ContextBase ctx) {
        return new String[0];
    }
}
