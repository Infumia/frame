package net.infumia.frame.annotation.decorator.element.itemstack;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.infumia.frame.annotation.config.ConfigKeyProvider;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementProvideConfigKey {
    Class<? extends ConfigKeyProvider> value();

    boolean cache() default false;
}
