package net.infumia.frame.annotations;

import net.infumia.frame.context.ContextBase;

public interface Provider<C extends ContextBase, T> {
    T provide(C context);
}
