package net.infumia.frame.annotations.provider.inferred;

import net.infumia.frame.annotations.provider.Provider;
import net.infumia.frame.context.ContextBase;

abstract class InferredProvider<C extends ContextBase, T> implements Provider<C, T> {

    @Override
    public final T provide(final C context) {
        throw new UnsupportedOperationException(
            "This provider is not intended to be used directly."
        );
    }
}
