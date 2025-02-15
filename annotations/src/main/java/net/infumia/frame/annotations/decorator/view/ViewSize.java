package net.infumia.frame.annotations.decorator.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.infumia.frame.annotations.provider.Provider;
import net.infumia.frame.annotations.provider.inferred.InferredIntegerProvider;
import net.infumia.frame.annotations.provider.inferred.InferredStringProvider;
import net.infumia.frame.context.ContextBase;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewSize {
    int value() default ViewSize.INFERRED_VALUE;

    Class<
        ? extends Provider<ContextBase, Integer>
    > provider() default InferredIntegerProvider.class;

    String configKey() default ViewSize.INFERRED_CONFIG_KEY;

    Class<
        ? extends Provider<ContextBase, String>
    > configKeyProvider() default InferredStringProvider.class;

    int INFERRED_VALUE = -1;

    String INFERRED_CONFIG_KEY = "__INFERRED_CONFIG_KEY__";
}
