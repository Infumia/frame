package net.infumia.frame.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.infumia.frame.Pair;
import net.infumia.frame.annotations.inferred.InferredCharacterProvider;
import net.infumia.frame.annotations.inferred.InferredIntegerProvider;
import net.infumia.frame.annotations.inferred.InferredSlotPositionProvider;
import net.infumia.frame.annotations.inferred.InferredStringProvider;
import net.infumia.frame.context.ContextBase;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementSlot {
    int index() default ElementSlot.INFERRED_INDEX;

    char layout() default ElementSlot.INFERRED_LAYOUT;

    int row() default ElementSlot.INFERRED_ROW;

    int column() default ElementSlot.INFERRED_COLUMN;

    boolean firstSlot() default false;

    boolean lastSlot() default false;

    boolean availableSlot() default false;

    boolean resultSlot() default false;

    String configKey() default "";

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

    char INFERRED_LAYOUT = ' ';
}
