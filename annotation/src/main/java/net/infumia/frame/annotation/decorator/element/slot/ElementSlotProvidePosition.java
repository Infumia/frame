package net.infumia.frame.annotation.decorator.element.slot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.infumia.frame.api.element.provider.slot.ElementSlotProviderPosition;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementSlotProvidePosition {
    Class<? extends ElementSlotProviderPosition> value();

    boolean cache() default false;
}
