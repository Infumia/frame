package net.infumia.frame;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import org.junit.jupiter.api.Test;

final class PreconditionsTest {

    @Test
    void testArgument_true() {
        assertDoesNotThrow(() -> Preconditions.argument(true, "This should not throw"));
    }

    @Test
    void testArgument_false() {
        final Exception exception = assertThrows(IllegalArgumentException.class, () ->
            Preconditions.argument(false, "Error message with %s", "arg")
        );
        assertEquals("Error message with arg", exception.getMessage());
    }

    @Test
    void testArgumentNotNull_nonNull() {
        final String obj = "test";
        assertSame(obj, Preconditions.argumentNotNull(obj, "Object should not be null"));
    }

    @Test
    void testArgumentNotNull_null() {
        final Exception exception = assertThrows(IllegalArgumentException.class, () ->
            Preconditions.argumentNotNull(null, "Error with %s and %s", "param1", "param2")
        );
        assertEquals("Error with param1 and param2", exception.getMessage());
    }

    @Test
    void testState_true() {
        assertDoesNotThrow(() -> Preconditions.state(true, "This should not throw for state"));
    }

    @Test
    void testState_false() {
        final Exception exception = assertThrows(IllegalStateException.class, () ->
            Preconditions.state(false, "State error with %s", "stateArg")
        );
        assertEquals("State error with stateArg", exception.getMessage());
    }

    @Test
    void testStateNotNull_nonNull() {
        final Integer obj = 123;
        assertSame(obj, Preconditions.stateNotNull(obj, "State object should not be null"));
    }

    @Test
    void testStateNotNull_null() {
        final Exception exception = assertThrows(IllegalStateException.class, () ->
            Preconditions.stateNotNull(null, "State error for null object %s", "arg")
        );
        assertEquals("State error for null object arg", exception.getMessage());
    }

    @Test
    void testConstructor() {
        // Test that the private constructor throws an IllegalStateException
        // This requires reflection as the constructor is private.
        assertThrows(ReflectiveOperationException.class, () -> {
            final Constructor<Preconditions> constructor =
                Preconditions.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }
}
