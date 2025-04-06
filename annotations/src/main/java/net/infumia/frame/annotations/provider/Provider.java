package net.infumia.frame.annotations.provider;

import net.infumia.frame.context.ContextBase;

public interface Provider<C extends ContextBase, T> {
    T provide(C context);
}
