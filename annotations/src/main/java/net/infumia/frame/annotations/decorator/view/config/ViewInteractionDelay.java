package net.infumia.frame.annotations.decorator.view.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.infumia.frame.annotations.provider.Provider;
import net.infumia.frame.annotations.provider.inferred.InferredLongProvider;
import net.infumia.frame.annotations.provider.inferred.InferredStringProvider;
import net.infumia.frame.context.ContextBase;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewInteractionDelay {
    long value() default ViewInteractionDelay.INFERRED_VALUE;

    Class<? extends Provider<ContextBase, Long>> provider() default InferredLongProvider.class;

    String configKey() default ViewInteractionDelay.INFERRED_CONFIG_KEY;

    Class<
        ? extends Provider<ContextBase, String>
    > configKeyProvider() default InferredStringProvider.class;

    long INFERRED_VALUE = -1;

    String INFERRED_CONFIG_KEY = "__INFERRED_CONFIG_KEY__";
}
