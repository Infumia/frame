package net.infumia.frame;

public interface Cloned<S extends Cloned<S>> {
    S cloned();
}
