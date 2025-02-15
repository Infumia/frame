package net.infumia.frame.element;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

public interface ElementContainer {
    @NotNull
    @UnmodifiableView
    List<Element> elements();
}
