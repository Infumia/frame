package net.infumia.frame.typedkey;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import org.junit.jupiter.api.Test;

final class TypedKeyAllTest {

    @Test
    void testTypedKeyCreation() {
        final TypedKey<String> stringKey = TypedKey.of(String.class, "testKey");
        assertNotNull(stringKey, "TypedKey should not be null");
        assertEquals("testKey", stringKey.key(), "Key name should match");
        assertEquals(String.class, stringKey.cls().getType(), "Key type should match");
    }

    @Test
    void testTypedKeyEqualsAndHashCode() {
        final TypedKey<String> key1 = TypedKey.of(String.class, "myKey");
        final TypedKey<String> key2 = TypedKey.of(String.class, "myKey");
        final TypedKey<String> key3 = TypedKey.of(String.class, "anotherKey");

        assertEquals(key1, key2, "Keys with same name and type should be equal");
        assertEquals(key1.hashCode(), key2.hashCode(), "HashCodes of equal keys should be equal");

        assertNotEquals(key1, key3, "Keys with different names should not be equal");
        assertNotEquals(null, key1, "Key should not be equal to null");
    }

    @Test
    void testTypedKeyStorageFactory() {
        final TypedKeyStorageFactory factory = TypedKeyStorageFactory.create();
        assertNotNull(factory, "Default factory should not be null");
        assertInstanceOf(
            TypedKeyStorageFactoryImpl.class,
            factory,
            "Default factory should be an instance of TypedKeyStorageFactoryImpl"
        );
        final TypedKeyStorage storage = factory.create(new HashMap<>());
        assertNotNull(storage, "Created storage should not be null");
        assertInstanceOf(
            TypedKeyStorageImpl.class,
            storage,
            "Created storage should be an instance of TypedKeyStorageImpl"
        );
    }

    @Test
    void testTypedKeyStorageImpl_getSetRemoveContains() {
        final TypedKeyStorage storage = TypedKeyStorageFactory.create().create(new HashMap<>());
        final TypedKey<String> strKey = TypedKey.of(String.class, "name");
        final TypedKey<Integer> intKey = TypedKey.of(int.class, "age");

        // Test set and get
        storage.put(strKey, "John Doe");
        storage.put(intKey, 30);
        assertEquals("John Doe", storage.get(strKey), "Should retrieve the set string value");
        assertEquals(30, storage.get(intKey), "Should retrieve the set integer value");

        // Test get non-existent
        final TypedKey<Double> doubleKey = TypedKey.of(double.class, "height");
        assertNull(storage.get(doubleKey), "Should return empty for non-existent key");

        // Test contains
        assertTrue(storage.contains(strKey), "Should contain strKey");
        assertFalse(storage.contains(doubleKey), "Should not contain doubleKey");

        // Test remove
        assertTrue(storage.remove(strKey), "Remove should return the removed value");
        assertFalse(storage.contains(strKey), "Should not contain strKey after removal");
        assertNull(storage.get(strKey), "Get after removal should return empty");
        assertFalse(storage.remove(strKey), "Remove non-existent key should return empty");
    }

    @Test
    void testTypedKeyStorageImpl_getOrSet() {
        final TypedKeyStorage storage = TypedKeyStorageFactory.create().create(new HashMap<>());
        final TypedKey<String> key = TypedKey.of(String.class, "city");

        final String value1 = storage.computeIfAbsent(key, () -> "New York");
        assertEquals("New York", value1, "Should set and return default value if key not present");
        assertEquals("New York", storage.get(key), "Key should be set after getOrSet");

        final String value2 = storage.computeIfAbsent(key, () -> "London");
        assertEquals("New York", value2, "Should return existing value if key is present");
    }

    @Test
    void testTypedKeyStorageImmutable_empty() {
        final TypedKeyStorageImmutable immutable = TypedKeyStorageFactory.create()
            .createImmutableBuilder(new HashMap<>())
            .build();
        assertNotNull(immutable);
        final TypedKey<String> key = TypedKey.of(String.class, "test");
        assertFalse(immutable.contains(key), "Empty immutable storage should not contain any key");
    }

    @Test
    void testTypedKeyStorageImmutableBuilder_build() {
        final TypedKey<String> nameKey = TypedKey.of(String.class, "name");
        final TypedKey<Integer> versionKey = TypedKey.of(int.class, "version");

        final TypedKeyStorageImmutable immutable = TypedKeyStorageFactory.create()
            .createImmutableBuilder(new HashMap<>())
            .add(nameKey, "ImmutableTest")
            .add(versionKey, 1)
            .build();

        assertNotNull(immutable);
        assertEquals("ImmutableTest", immutable.get(nameKey));
        assertEquals(1, immutable.get(versionKey));
        assertTrue(immutable.contains(nameKey));

        final TypedKey<Double> nonExistentKey = TypedKey.of(double.class, "price");
        assertFalse(immutable.contains(nonExistentKey));
    }

    @Test
    void testTypedKeyStorageImmutableBuilder_fromStorage() {
        final TypedKey<String> k1 = TypedKey.of(String.class, "k1");
        final TypedKey<Boolean> k2 = TypedKey.of(boolean.class, "k2");

        final TypedKeyStorageImmutable immutable = TypedKeyStorageFactory.create()
            .createImmutableBuilder(new HashMap<>())
            .add(k1, "value1")
            .add(k2, true)
            .build();

        assertEquals("value1", immutable.get(k1));
        assertEquals(true, immutable.get(k2));
    }

    @Test
    void testTypedKeyStorageImmutableImpl_keys() {
        final TypedKey<String> strKey = TypedKey.of(String.class, "name");
        final TypedKey<Integer> intKey = TypedKey.of(int.class, "age");
        final TypedKeyStorageImmutable immutable = TypedKeyStorageFactory.create()
            .createImmutableBuilder(new HashMap<>())
            .add(strKey, "Test")
            .add(intKey, 10)
            .build();

        assertEquals(2, immutable.keys().size());
        assertTrue(immutable.keys().contains(strKey));
        assertTrue(immutable.keys().contains(intKey));
    }
}
