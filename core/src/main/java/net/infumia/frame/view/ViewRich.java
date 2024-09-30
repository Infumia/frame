package net.infumia.frame.view;

import net.infumia.frame.context.view.ContextInitRich;
import org.jetbrains.annotations.NotNull;

public interface ViewRich extends View, ViewEventHandler {
    @Override
    @NotNull
    ContextInitRich context();
}
