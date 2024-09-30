package net.infumia.frame.annotation.decorator.config.transitive;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewTransitiveInitialDataConfigKey {
    String value();

    boolean cache() default false;
}
