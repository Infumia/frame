package net.infumia.frame.annotation.decorator.config.transitive;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.infumia.frame.annotation.config.ConfigKeyProvider;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewTransitiveInitialDataProvideConfigKey {
    Class<? extends ConfigKeyProvider> value();

    boolean cache() default false;
}