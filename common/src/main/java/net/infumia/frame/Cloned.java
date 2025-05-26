package net.infumia.frame;

/**
 * A cloned interface is a interface that is used to create cloned objects.
 *
 * @param <This> the type of the cloned object
 */
public interface Cloned<This extends Cloned<This>> {
    /**
     * Clones the object.
     *
     * @return the cloned object
     */
    This cloned();
}
