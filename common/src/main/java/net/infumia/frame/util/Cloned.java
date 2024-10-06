package net.infumia.frame.util;

import org.jetbrains.annotations.NotNull;

public interface Cloned<S extends Cloned<S>> {
    @NotNull
    S cloned();
}
