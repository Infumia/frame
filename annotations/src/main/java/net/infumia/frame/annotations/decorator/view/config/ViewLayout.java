package net.infumia.frame.annotations.decorator.view.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.infumia.frame.annotations.provider.Provider;
import net.infumia.frame.annotations.provider.inferred.InferredStringArrayProvider;
import net.infumia.frame.annotations.provider.inferred.InferredStringProvider;
import net.infumia.frame.context.ContextBase;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewLayout {
    String[] value() default {};

    Class<
        ? extends Provider<ContextBase, String[]>
    > provider() default InferredStringArrayProvider.class;

    String configKey() default ViewLayout.INFERRED_CONFIG_KEY;

    Class<
        ? extends Provider<ContextBase, String>
    > configKeyProvider() default InferredStringProvider.class;

    String INFERRED_CONFIG_KEY = "__INFERRED_CONFIG_KEY__";
}
