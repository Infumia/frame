package net.infumia.frame.annotations.decorator.config.type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.type.InvType;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewTypeProvide {
    Class<? extends Function<ContextBase, InvType>> value();

    boolean cache() default false;
}
