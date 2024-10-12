package net.infumia.frame.annotations.decorator.config.title;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;
import net.infumia.frame.api.context.ContextBase;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewTitleProvide {
    Class<? extends Function<ContextBase, Object>> value();

    boolean cache() default false;
}
