package net.infumia.frame.annotations.decorator.element.slot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.infumia.frame.annotations.config.ConfigKeyProvider;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementSlotProvideConfigKey {
    Class<? extends ConfigKeyProvider> value();

    boolean cache() default false;
}