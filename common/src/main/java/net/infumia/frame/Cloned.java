package net.infumia.frame;

public interface Cloned<This extends Cloned<This>> {
    This cloned();
}
