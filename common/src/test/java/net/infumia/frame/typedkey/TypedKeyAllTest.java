package net.infumia.frame.typedkey;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Optional;
import org.junit.jupiter.api.Test;

final class TypedKeyAllTest {

    @Test
    void testTypedKeyCreation() {
        final TypedKey<String> stringKey = TypedKey.of(String.class, "testKey");
        assertNotNull(stringKey, "TypedKey should not be null");
        assertEquals("testKey", stringKey.key(), "Key name should match");
        assertEquals(String.class, stringKey.cls(), "Key type should match");
    }

    @Test
    void testTypedKeyToString() {
        final TypedKey<Integer> intKey = TypedKey.of(int.class, "count");
        final String expected = "TypedKey[key=count, type=class java.lang.Integer]";
        assertEquals(expected, intKey.toString(), "toString output should match expected format");
    }

    @Test
    void testTypedKeyEqualsAndHashCode() {
        final TypedKey<String> key1 = TypedKey.of(String.class, "myKey");
        final TypedKey<String> key2 = TypedKey.of(String.class, "myKey");
        final TypedKey<String> key3 = TypedKey.of(String.class, "anotherKey");
        final TypedKey<Integer> key4 = TypedKey.of(int.class, "myKey");

        assertEquals(key1, key2, "Keys with same name and type should be equal");
        assertEquals(key1.hashCode(), key2.hashCode(), "HashCodes of equal keys should be equal");

        assertNotEquals(key1, key3, "Keys with different names should not be equal");
        assertNotEquals(key1, key4, "Keys with different types should not be equal");
        assertNotEquals(key1, null, "Key should not be equal to null");
        assertNotEquals(key1, "myKey", "Key should not be equal to an object of different type");
    }

    @Test
    void testTypedKeyStorageFactory() {
        final TypedKeyStorageFactory factory = TypedKeyStorageFactory.create();
        assertNotNull(factory, "Default factory should not be null");
        assertTrue(
            factory instanceof TypedKeyStorageFactoryImpl,
            "Default factory should be an instance of TypedKeyStorageFactoryImpl"
        );
        final TypedKeyStorage storage = factory.create(new HashMap<>());
        assertNotNull(storage, "Created storage should not be null");
        assertTrue(
            storage instanceof TypedKeyStorageImpl,
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

        final String value1 = storage.getOrSet(key, () -> "New York");
        assertEquals("New York", value1, "Should set and return default value if key not present");
        assertEquals(Optional.of("New York"), storage.get(key), "Key should be set after getOrSet");

        final String value2 = storage.getOrSet(key, () -> "London");
        assertEquals("New York", value2, "Should return existing value if key is present");
    }

    @Test
    void testTypedKeyStorageImpl_getAndSet() {
        final TypedKeyStorage storage = TypedKeyStorageFactory.defaultFactory().create();
        final TypedKey<Integer> key = TypedKey.of("score", Integer.class);

        final Optional<Integer> prevValue1 = storage.getAndSet(key, 100);
        assertFalse(prevValue1.isPresent(), "Should return empty Optional if key not present initially");
        assertEquals(Optional.of(100), storage.get(key), "Key should be set to new value");

        final Optional<Integer> prevValue2 = storage.getAndSet(key, 200);
        assertEquals(Optional.of(100), prevValue2, "Should return previous value");
        assertEquals(Optional.of(200), storage.get(key), "Key should be updated to new value");
    }

    @Test
    void testTypedKeyStorageImmutable_empty() {
        final TypedKeyStorageImmutable immutable = TypedKeyStorageImmutable.empty();
        assertNotNull(immutable);
        final TypedKey<String> key = TypedKey.of("test", String.class);
        assertFalse(immutable.get(key).isPresent(), "Empty immutable storage should not contain any key");
        assertFalse(immutable.contains(key), "Empty immutable storage should not contain any key");
    }

    @Test
    void testTypedKeyStorageImmutableBuilder_build() {
        final TypedKey<String> nameKey = TypedKey.of("name", String.class);
        final TypedKey<Integer> versionKey = TypedKey.of("version", Integer.class);

        final TypedKeyStorageImmutable immutable = TypedKeyStorageImmutable
            .builder()
            .put(nameKey, "ImmutableTest")
            .put(versionKey, 1)
            .build();

        assertNotNull(immutable);
        assertEquals(Optional.of("ImmutableTest"), immutable.get(nameKey));
        assertEquals(Optional.of(1), immutable.get(versionKey));
        assertTrue(immutable.contains(nameKey));

        final TypedKey<Double> nonExistentKey = TypedKey.of("price", Double.class);
        assertFalse(immutable.get(nonExistentKey).isPresent());
        assertFalse(immutable.contains(nonExistentKey));
    }

    @Test
    void testTypedKeyStorageImmutableBuilder_fromStorage() {
        final TypedKeyStorage mutableStorage = TypedKeyStorageFactory.defaultFactory().create();
        final TypedKey<String> k1 = TypedKey.of("k1", String.class);
        final TypedKey<Boolean> k2 = TypedKey.of("k2", Boolean.class);
        mutableStorage.set(k1, "value1");
        mutableStorage.set(k2, true);

        final TypedKeyStorageImmutable immutable = TypedKeyStorageImmutable.builder().putAll(mutableStorage).build();

        assertEquals(Optional.of("value1"), immutable.get(k1));
        assertEquals(Optional.of(true), immutable.get(k2));
    }

     @Test
    void testTypedKeyStorageImmutableImpl_keys() {
        final TypedKey<String> strKey = TypedKey.of("name", String.class);
        final TypedKey<Integer> intKey = TypedKey.of("age", Integer.class);
        final TypedKeyStorageImmutable immutable = TypedKeyStorageImmutable
            .builder()
            .put(strKey, "Test")
            .put(intKey, 10)
            .build();
        assertEquals(2, immutable.keys().size());
        assertTrue(immutable.keys().contains(strKey));
        assertTrue(immutable.keys().contains(intKey));
    }

} 