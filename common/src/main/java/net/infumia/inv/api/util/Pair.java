package net.infumia.inv.api.util;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public final class Pair<F, S> {

    @NotNull
    private final F first;

    @NotNull
    private final S second;

    private Pair(@NotNull final F first, @NotNull final S second) {
        this.first = first;
        this.second = second;
    }

    @NotNull
    public static <F, S> Pair<F, S> of(@NotNull final F first, @NotNull final S second) {
        return new Pair<>(first, second);
    }

    @NotNull
    public static <K, V> Collector<Pair<? extends K, ? extends V>, ?, Map<K, V>> mapCollector() {
        return Collectors.toMap(Pair::first, Pair::second);
    }

    @NotNull
    public F first() {
        return this.first;
    }

    @NotNull
    public S second() {
        return this.second;
    }

    @Override
    public String toString() {
        return "Pair{" + "first=" + this.first + ", second=" + this.second + '}';
    }
}
