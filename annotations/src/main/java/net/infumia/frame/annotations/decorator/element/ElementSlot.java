package net.infumia.frame.annotations.decorator.element;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.infumia.frame.annotations.provider.Provider;
import net.infumia.frame.annotations.provider.inferred.InferredCharacterProvider;
import net.infumia.frame.annotations.provider.inferred.InferredIntegerProvider;
import net.infumia.frame.annotations.provider.inferred.InferredSlotPositionProvider;
import net.infumia.frame.annotations.provider.inferred.InferredStringProvider;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.util.Pair;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementSlot {
    int index() default ElementSlot.INFERRED_INDEX;

    char layout() default ElementSlot.INFERRED_LAYOUT;

    int row() default ElementSlot.INFERRED_ROW;

    int column() default ElementSlot.INFERRED_COLUMN;

    String configKey();

    Class<
        ? extends Provider<ContextBase, String>
    > configKeyProvider() default InferredStringProvider.class;

    Class<
        ? extends Provider<ContextBase, Integer>
    > indexProvider() default InferredIntegerProvider.class;

    Class<
        ? extends Provider<ContextBase, Character>
    > layoutProvider() default InferredCharacterProvider.class;

    Class<
        ? extends Provider<ContextBase, Pair<Integer, Integer>>
    > positionProvider() default InferredSlotPositionProvider.class;

    int INFERRED_INDEX = -1;

    int INFERRED_ROW = -1;

    int INFERRED_COLUMN = -1;

    char INFERRED_LAYOUT = ':';
}
