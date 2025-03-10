package net.infumia.frame.annotations.decorator.element.vault;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.infumia.frame.annotations.provider.Provider;
import net.infumia.frame.annotations.provider.inferred.InferredCharacterProvider;
import net.infumia.frame.annotations.provider.inferred.InferredStringProvider;
import net.infumia.frame.context.ContextBase;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementVault {
    char layout() default ElementVault.INFERRED_LAYOUT;

    String configKey() default "";

    Class<
        ? extends Provider<ContextBase, String>
    > configKeyProvider() default InferredStringProvider.class;

    Class<
        ? extends Provider<ContextBase, Character>
    > layoutProvider() default InferredCharacterProvider.class;

    char INFERRED_LAYOUT = ':';
}
