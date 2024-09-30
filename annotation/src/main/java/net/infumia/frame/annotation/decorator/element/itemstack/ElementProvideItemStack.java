package net.infumia.frame.annotation.decorator.element.itemstack;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.infumia.frame.api.element.provider.itemstack.ElementProviderItemStack;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementProvideItemStack {
    Class<? extends ElementProviderItemStack> value();

    boolean cache() default false;
}
