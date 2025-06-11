package net.infumia.frame.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementPaginationBack {
    String value() default ElementPaginationBack.FIRST_PAGINATION_KEY;

    boolean tryHide() default true;

    String FIRST_PAGINATION_KEY = "__FIRST_PAGINATION__";
}
