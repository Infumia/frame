package net.infumia.frame.annotations.provider.inferred;

import net.infumia.frame.annotations.provider.Provider;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.type.InvType;
import org.jetbrains.annotations.NotNull;

public final class InferredInvTypeProvider implements Provider<ContextBase, InvType> {

    private InferredInvTypeProvider() {}

    @NotNull
    @Override
    public InvType provide(@NotNull final ContextBase ctx) {
        return InvType.CHEST;
    }
}
