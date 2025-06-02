package net.infumia.frame;

/**
 * A keyed interface is an interface that is used to create keyed objects.
 *
 * @param <T> the type of the key
 */
public interface Keyed<T> {
    /**
     * Gets the key of the object.
     *
     * @return the key of the object
     */
    T key();
}
