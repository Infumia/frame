package net.infumia.frame;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;

final class LazyTest {

    @Test
    void testLazyInitialization() {
        final AtomicInteger counter = new AtomicInteger(0);
        final Supplier<String> supplier = () -> {
            counter.incrementAndGet();
            return "testValue";
        };
        final Lazy<String> lazy = Lazy.of(supplier);
        assertNotNull(lazy, "Lazy should not be null");
        assertEquals(0, counter.get(), "Supplier should not be called on initialization");
    }

    @Test
    void testGet() {
        final AtomicInteger counter = new AtomicInteger(0);
        final Supplier<String> supplier = () -> {
            counter.incrementAndGet();
            return "testValue";
        };
        final Lazy<String> lazy = Lazy.of(supplier);
        assertEquals("testValue", lazy.get(), "First get should return the value from supplier");
        assertEquals(1, counter.get(), "Supplier should be called once");
        assertEquals("testValue", lazy.get(), "Second get should return the cached value");
        assertEquals(1, counter.get(), "Supplier should still be called only once");
    }

    @Test
    void testGetWithNullSupplierResult() {
        final AtomicInteger counter = new AtomicInteger(0);
        final Supplier<String> supplier = () -> {
            counter.incrementAndGet();
            return null;
        };
        final Lazy<String> lazy = Lazy.of(supplier);
        assertNull(lazy.get(), "First get should return null from supplier");
        assertEquals(1, counter.get(), "Supplier should be called once");
        assertNull(lazy.get(), "Second get should return the cached null value");
        assertEquals(1, counter.get(), "Supplier should still be called only once");
    }

    @Test
    void testNullSupplier() {
        assertThrows(
            NullPointerException.class,
            () -> Lazy.of(null),
            "Lazy.of(null) should throw NullPointerException"
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
        final Lazy<String> lazy = Lazy.of(supplier);
        final Thread t1 = new Thread(lazy::get);
        final Thread t2 = new Thread(lazy::get);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        assertEquals("testValue", lazy.get(), "Value should be correct after concurrent access");
        assertEquals(1, counter.get(), "Supplier should be called only once with concurrent access");
    }
} 