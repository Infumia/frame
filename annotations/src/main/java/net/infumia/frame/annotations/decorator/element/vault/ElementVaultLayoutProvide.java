package net.infumia.frame.annotations.decorator.element.vault;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.infumia.frame.annotations.provider.vault.ElementVaultLayoutProvider;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementVaultLayoutProvide {
    Class<? extends ElementVaultLayoutProvider> value();

    boolean cache() default false;
}
