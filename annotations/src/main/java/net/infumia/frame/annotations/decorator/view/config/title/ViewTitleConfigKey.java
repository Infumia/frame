package net.infumia.frame.annotations.decorator.view.config.title;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewTitleConfigKey {
    String value();

    boolean cache() default false;
}
