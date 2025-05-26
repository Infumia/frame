package net.infumia.frame;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;

final class CacheTest {

    @Test
    void testCacheInitialization() {
        final AtomicInteger counter = new AtomicInteger(0);
        final Supplier<String> supplier = () -> {
            counter.incrementAndGet();
            return "testValue";
        };
        final Cache<String> cache = Cache.of(supplier);
        assertNotNull(cache, "Cache should not be null");
    }

    @Test
    void testGet() {
        final AtomicInteger counter = new AtomicInteger(0);
        final Supplier<String> supplier = () -> {
            counter.incrementAndGet();
            return "testValue";
        };
        final Cache<String> cache = Cache.of(supplier);
        assertEquals("testValue", cache.get(), "First get should return the value from supplier");
        assertEquals(1, counter.get(), "Supplier should be called once");
        assertEquals("testValue", cache.get(), "Second get should return the cached value");
        assertEquals(1, counter.get(), "Supplier should still be called only once");
    }

    @Test
    void testIfPresent() {
        final Supplier<String> supplier = () -> "testValue";
        final Cache<String> cache = Cache.of(supplier);
        assertFalse(cache.ifPresent().isPresent(), "ifPresent should be empty before first get");
        cache.get();
        assertTrue(cache.ifPresent().isPresent(), "ifPresent should not be empty after first get");
        assertEquals("testValue", cache.ifPresent().get(), "ifPresent should return the cached value");
    }

    @Test
    void testInvalidate() {
        final AtomicInteger counter = new AtomicInteger(0);
        final Supplier<String> supplier = () -> {
            counter.incrementAndGet();
            return "testValue";
        };
        final Cache<String> cache = Cache.of(supplier);
        cache.get();
        assertEquals(1, counter.get(), "Supplier should be called once");
        cache.invalidate();
        assertFalse(cache.ifPresent().isPresent(), "ifPresent should be empty after invalidate");
        cache.get();
        assertEquals("testValue", cache.get(), "get should return new value after invalidate");
        assertEquals(2, counter.get(), "Supplier should be called twice after invalidate and get");
    }

    @Test
    void testGetWithNullSupplierResult() {
        final AtomicInteger counter = new AtomicInteger(0);
        final Supplier<String> supplier = () -> {
            counter.incrementAndGet();
            return null;
        };
        final Cache<String> cache = Cache.of(supplier);
        assertNull(cache.get(), "First get should return null from supplier");
        assertEquals(1, counter.get(), "Supplier should be called once");
        assertNull(cache.get(), "Second get should return the cached null value");
        assertEquals(1, counter.get(), "Supplier should still be called only once");
        cache.invalidate();
        assertNull(cache.get(), "Get after invalidate should return null from supplier again");
        assertEquals(2, counter.get(), "Supplier should be called twice after invalidate and get");

    }

    @Test
    void testNullSupplier() {
        assertThrows(
            NullPointerException.class,
            () -> Cache.of(null),
            "Cache.of(null) should throw NullPointerException"
        );
    }

    @Test
    void testConcurrentGet() throws InterruptedException {
        final AtomicInteger counter = new AtomicInteger(0);
        final Supplier<String> supplier = () -> {
            try {
                // Simulate some work
                Thread.sleep(100);
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            counter.incrementAndGet();
            return "testValue";
        };
        final Cache<String> cache = Cache.of(supplier);
        final Thread t1 = new Thread(cache::get);
        final Thread t2 = new Thread(cache::get);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        assertEquals("testValue", cache.get(), "Value should be correct after concurrent access");
        assertEquals(1, counter.get(), "Supplier should be called only once with concurrent access");
    }
} 