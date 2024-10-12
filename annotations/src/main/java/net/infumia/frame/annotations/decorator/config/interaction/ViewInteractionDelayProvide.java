package net.infumia.frame.annotations.decorator.config.interaction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;
import net.infumia.frame.api.context.ContextBase;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewInteractionDelayProvide {
    Class<? extends Function<ContextBase, Long>> value();

    boolean cache() default false;
}
