package net.infumia.frame.annotations;

import java.lang.annotation.Annotation;
import net.infumia.frame.view.View;
import org.jetbrains.annotations.NotNull;

public interface ViewDecorator<A extends Annotation> {
    void decorate(@NotNull View view, @NotNull A annotation);
}
