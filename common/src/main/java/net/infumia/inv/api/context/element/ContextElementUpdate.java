package net.infumia.inv.api.context.element;

import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.element.Element;
import net.infumia.inv.api.service.Cancellable;
import org.jetbrains.annotations.NotNull;

public interface ContextElementUpdate extends ContextRender, Cancellable {
    @NotNull
    Element element();

    boolean forced();
}
