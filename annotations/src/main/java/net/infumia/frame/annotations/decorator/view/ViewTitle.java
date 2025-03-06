package net.infumia.frame.annotations.decorator.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.infumia.frame.annotations.provider.Provider;
import net.infumia.frame.annotations.provider.inferred.InferredStringProvider;
import net.infumia.frame.context.ContextBase;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewTitle {
    String value() default ViewTitle.INFERRED_VALUE;

    Class<? extends Provider<ContextBase, String>> provider() default InferredStringProvider.class;

    String configKey() default ViewTitle.INFERRED_CONFIG_KEY;

    Class<
        ? extends Provider<ContextBase, String>
    > configKeyProvider() default InferredStringProvider.class;

    String INFERRED_VALUE = "__INFERRED_VALUE__";

    String INFERRED_CONFIG_KEY = "__INFERRED_CONFIG_KEY__";
}
