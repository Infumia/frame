package net.infumia.frame.annotations.decorator.element.pagination;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementPaginationAdvance {
    String value() default "";

    boolean hideIfCannot() default true;
}