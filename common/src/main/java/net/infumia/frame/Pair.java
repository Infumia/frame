package net.infumia.frame;

import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class Pair<F, S> {

    private final F first;
    private final S second;

    private Pair(final F first, final S second) {
        this.first = first;
        this.second = second;
    }

    public static <F, S> Pair<F, S> of(final F first, final S second) {
        return new Pair<>(first, second);
    }

    public static <K, V> Collector<Pair<? extends K, ? extends V>, ?, Map<K, V>> mapCollector() {
        return Collectors.toMap(Pair::first, Pair::second);
    }

    public F first() {
        return this.first;
    }

    public S second() {
        return this.second;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Pair.class.getSimpleName() + "[", "]")
            .add("first=" + this.first)
            .add("second=" + this.second)
            .toString();
    }
}
