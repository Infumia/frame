package net.infumia.frame.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.infumia.frame.annotations.inferred.InferredStringArrayProvider;
import net.infumia.frame.annotations.inferred.InferredStringProvider;
import net.infumia.frame.context.ContextBase;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewLayout {
    String[] value() default {};

    Class<
        ? extends Provider<ContextBase, String[]>
    > provider() default InferredStringArrayProvider.class;

    String configKey() default "";

    Class<
        ? extends Provider<ContextBase, String>
    > configKeyProvider() default InferredStringProvider.class;
}
