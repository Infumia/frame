package net.infumia.frame.injector;

@FunctionalInterface
public interface Injector<C> {
    Object inject(InjectionRequest<C> ctx);
}
