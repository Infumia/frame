package net.infumia.frame.annotations.decorator.view.config.size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;
import net.infumia.frame.context.ContextBase;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewSizeProvider {
    Class<? extends Function<ContextBase, Integer>> value();

    boolean cache() default false;
}
