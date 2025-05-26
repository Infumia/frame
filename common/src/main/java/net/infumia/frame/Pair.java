package net.infumia.frame;

import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * A pair is a class that is used to create pairs of objects.
 *
 * @param <F> the type of the first object
 * @param <S> the type of the second object
 */
public final class Pair<F, S> {

    private final F first;
    private final S second;

    private Pair(final F first, final S second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Creates a new pair.
     *
     * @param first the first object
     * @param second the second object
     * @return the new pair
     */
    public static <F, S> Pair<F, S> of(final F first, final S second) {
        return new Pair<>(first, second);
    }

    /**
     * Creates a new pair collector.
     *
     * @param <K> the type of the key
     * @param <V> the type of the value
     * @return the new pair collector
     */
    public static <K, V> Collector<Pair<? extends K, ? extends V>, ?, Map<K, V>> mapCollector() {
        return Collectors.toMap(Pair::first, Pair::second);
    }

    /**
     * Gets the first object.
     *
     * @return the first object
     */
    public F first() {
        return this.first;
    }

    /**
     * Gets the second object.
     *
     * @return the second object
     */
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
