package net.infumia.frame.annotation.decorator.element.slot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.infumia.frame.api.element.provider.slot.ElementSlotProviderIndex;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementSlotProvideIndex {
    Class<? extends ElementSlotProviderIndex> value();

    boolean cache() default false;
}
