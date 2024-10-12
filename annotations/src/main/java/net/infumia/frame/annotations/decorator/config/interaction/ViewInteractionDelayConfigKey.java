package net.infumia.frame.annotations.decorator.config.interaction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewInteractionDelayConfigKey {
    String value();

    boolean cache() default false;
}
