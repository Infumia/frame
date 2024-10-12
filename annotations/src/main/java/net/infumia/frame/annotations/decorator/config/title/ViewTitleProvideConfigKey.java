package net.infumia.frame.annotations.decorator.config.title;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.infumia.frame.annotations.config.ConfigKeyProvider;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewTitleProvideConfigKey {
    Class<? extends ConfigKeyProvider> value();

    boolean cache() default false;
}