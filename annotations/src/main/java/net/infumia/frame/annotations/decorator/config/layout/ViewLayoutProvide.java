package net.infumia.frame.annotations.decorator.config.layout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;
import net.infumia.frame.api.context.ContextBase;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewLayoutProvide {
    Class<? extends Function<ContextBase, String[]>> value();

    boolean cache() default false;
}
