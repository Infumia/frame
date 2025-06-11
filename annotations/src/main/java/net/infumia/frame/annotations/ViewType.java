package net.infumia.frame.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.infumia.frame.annotations.inferred.InferredInvTypeProvider;
import net.infumia.frame.annotations.inferred.InferredStringProvider;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.type.InvType;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewType {
    InvType value() default InvType.CHEST;

    Class<
        ? extends Provider<ContextBase, InvType>
    > provider() default InferredInvTypeProvider.class;

    String configKey() default "";

    Class<
        ? extends Provider<ContextBase, String>
    > configKeyProvider() default InferredStringProvider.class;
}
