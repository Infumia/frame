package net.infumia.frame.annotation.decorator.config.transitive;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;
import net.infumia.frame.api.context.ContextBase;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewTransitiveInitialDataProvide {
    Class<? extends Function<ContextBase, Boolean>> value();

    boolean cache() default false;
}
