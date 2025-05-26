package net.infumia.frame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

final class ReflectionTest {

    // A dummy class for testing reflection
    private static class TestClass {

        private static final String STATIC_FINAL_FIELD = "staticFinalValue";
        private static String staticField = "staticValue";
        private final String instanceField = "instanceValue";

        private TestClass() {}

        private String privateMethod() {
            return "privateMethodResult";
        }
    }

    private static class EmptyClass {}

    // A class that doesn't exist, for testing negative cases
    private static final String NON_EXISTENT_CLASS_NAME = "net.infumia.frame.NonExistentTestClass";

    @Test
    void testHasClass_exists() {
        assertTrue(Reflection.hasClass(TestClass.class.getName()), "hasClass should return true for existing class");
        assertTrue(Reflection.hasClass("java.lang.String"), "hasClass should return true for java.lang.String");
    }

    @Test
    void testHasClass_doesNotExist() {
        assertFalse(Reflection.hasClass(NON_EXISTENT_CLASS_NAME), "hasClass should return false for non-existent class");
    }

    @Test
    void testFindClass_exists() {
        assertNotNull(Reflection.findClass(TestClass.class.getName()), "findClass should return a class for existing class name");
        assertEquals(String.class, Reflection.findClass("java.lang.String"), "findClass should return String class for java.lang.String");
    }

    @Test
    void testFindClass_doesNotExist() {
        assertNull(Reflection.findClass(NON_EXISTENT_CLASS_NAME), "findClass should return null for non-existent class name");
    }

    @Test
    void testFindInstanceFromField_staticFinalField() {
        final String value = Reflection.findInstanceFromField(TestClass.class.getName(), "STATIC_FINAL_FIELD");
        assertEquals(TestClass.STATIC_FINAL_FIELD, value, "Should retrieve static final field value");
    }

    @Test
    void testFindInstanceFromField_staticField() {
        TestClass.staticField = "newStaticValue"; // Modify for test
        final String value = Reflection.findInstanceFromField(TestClass.class.getName(), "staticField");
        assertEquals("newStaticValue", value, "Should retrieve static field value");
    }

    @Test
    void testFindInstanceFromField_classNotFound() {
        final Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> Reflection.findInstanceFromField(NON_EXISTENT_CLASS_NAME, "anyField")
        );
        assertTrue(exception.getMessage().contains("Class '" + NON_EXISTENT_CLASS_NAME + "' not found"));
    }

    @Test
    void testFindInstanceFromField_fieldNotFound() {
        final Exception exception = assertThrows(
            RuntimeException.class, // As per current implementation detail
            () -> Reflection.findInstanceFromField(TestClass.class.getName(), "nonExistentField")
        );
        assertTrue(exception.getMessage().contains("Field 'nonExistentField' not found"));
        assertNotNull(exception.getCause());
        assertTrue(exception.getCause() instanceof NoSuchFieldException);
    }

    @Test
    void testFindInstanceFromField_fieldIsNull() {
        // Temporarily set a static field to null for testing purposes
        // This requires a public static non-final field or a more complex setup
        // For simplicity, we'll assume we have a field that *can* be null.
        // Let's add a new class with a nullable static field.
        final class TestClassWithNullableField {
            public static String nullableStaticField = null;
        }

        final Exception exception = assertThrows(
            IllegalArgumentException.class,
            () ->
                Reflection.findInstanceFromField(
                    TestClassWithNullableField.class.getName(),
                    "nullableStaticField"
                )
        );
        assertTrue(
            exception.getMessage().contains("Field 'nullableStaticField' value is null")
        );
    }

    // Cannot directly test IllegalAccessException for get(null) on an instance field via findInstanceFromField,
    // as it specifically calls field.get(null), which is for static fields.
    // Testing IllegalAccessException for a private static field would be similar to fieldNotFound if it's truly inaccessible,
    // or would succeed if setAccessible(true) works, which is what the method does.

    @Test
    void testConstructor() {
        assertThrows(ReflectiveOperationException.class, () -> {
            final Constructor<Reflection> constructor = Reflection.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }
} 