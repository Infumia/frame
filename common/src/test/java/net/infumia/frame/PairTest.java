package net.infumia.frame;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

final class PairTest {

    @Test
    void testOf() {
        final Pair<String, Integer> pair = Pair.of("test", 123);
        assertNotNull(pair, "Pair.of should not return null");
        assertEquals("test", pair.first(), "First element should be 'test'");
        assertEquals(123, pair.second(), "Second element should be 123");
    }

    @Test
    void testOfWithNulls() {
        final Pair<String, Integer> pairWithNullFirst = Pair.of(null, 123);
        assertNull(pairWithNullFirst.first(), "First element should be null");
        assertEquals(123, pairWithNullFirst.second(), "Second element should be 123");

        final Pair<String, Integer> pairWithNullSecond = Pair.of("test", null);
        assertEquals("test", pairWithNullSecond.first(), "First element should be 'test'");
        assertNull(pairWithNullSecond.second(), "Second element should be null");

        final Pair<String, Integer> pairWithBothNull = Pair.of(null, null);
        assertNull(pairWithBothNull.first(), "First element should be null");
        assertNull(pairWithBothNull.second(), "Second element should be null");
    }

    @Test
    void testFirst() {
        final Pair<String, Integer> pair = Pair.of("expectedFirst", 99);
        assertEquals("expectedFirst", pair.first(), "first() should return the first element");
    }

    @Test
    void testSecond() {
        final Pair<String, Integer> pair = Pair.of("anyString", 777);
        assertEquals(777, pair.second(), "second() should return the second element");
    }

    @Test
    void testMapCollector() {
        final Pair<String, Integer> pair1 = Pair.of("one", 1);
        final Pair<String, Integer> pair2 = Pair.of("two", 2);
        final Pair<String, Integer> pair3 = Pair.of("three", 3);

        @SuppressWarnings("unchecked")
        final Map<String, Integer> map = Stream.of(pair1, pair2, pair3).collect(Pair.mapCollector());

        assertNotNull(map, "Collected map should not be null");
        assertEquals(3, map.size(), "Map size should be 3");
        assertEquals(1, map.get("one"), "Map should contain 'one' -> 1");
        assertEquals(2, map.get("two"), "Map should contain 'two' -> 2");
        assertEquals(3, map.get("three"), "Map should contain 'three' -> 3");
    }

    @Test
    void testMapCollectorWithDuplicateKeys() {
        final Pair<String, Integer> pair1 = Pair.of("one", 1);
        final Pair<String, Integer> pair2 = Pair.of("two", 2);
        final Pair<String, Integer> pair3 = Pair.of("one", 3); // Duplicate key

        @SuppressWarnings("unchecked")
        final Stream<Pair<String, Integer>> stream = Stream.of(pair1, pair2, pair3);

        // Collectors.toMap throws IllegalStateException on duplicate keys by default.
        // The current Pair.mapCollector() uses Collectors.toMap(Pair::first, Pair::second)
        // which will exhibit this behavior.
        assertThrows(
            IllegalStateException.class,
            () -> stream.collect(Pair.mapCollector()),
            "Collecting pairs with duplicate keys should throw IllegalStateException"
        );
    }

    @Test
    void testToString() {
        final Pair<String, Integer> pair = Pair.of("hello", 42);
        final String expectedString = "Pair[first=hello, second=42]";
        assertEquals(expectedString, pair.toString(), "toString() should produce the correct format");

        final Pair<String, Integer> pairWithNulls = Pair.of(null, null);
        final String expectedStringWithNulls = "Pair[first=null, second=null]";
        assertEquals(
            expectedStringWithNulls,
            pairWithNulls.toString(),
            "toString() should handle nulls correctly"
        );
    }
} 