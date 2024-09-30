package net.infumia.inv.core.view;

import net.infumia.inv.api.view.View;
import net.infumia.inv.core.context.view.ContextInitRich;
import org.jetbrains.annotations.NotNull;

public interface ViewRich extends View, ViewEventHandler {
    @Override
    @NotNull
    ContextInitRich context();
}
