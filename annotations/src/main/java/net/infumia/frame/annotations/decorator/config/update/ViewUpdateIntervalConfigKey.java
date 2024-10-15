package net.infumia.frame.annotations.decorator.config.update;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewUpdateIntervalConfigKey {
    String value();

    boolean cache() default false;
}
