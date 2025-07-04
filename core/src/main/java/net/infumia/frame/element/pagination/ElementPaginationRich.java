package net.infumia.frame.element.pagination;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.element.Element;
import net.infumia.frame.element.ElementEventHandlerHolder;
import net.infumia.frame.element.ElementRich;
import net.infumia.frame.state.State;
import org.jetbrains.annotations.NotNull;

public interface ElementPaginationRich
    extends ElementRich, ElementPagination, ElementEventHandlerHolder {
    @NotNull
    State<ElementPagination> associated();

    boolean pageWasChanged();

    void pageWasChanged(boolean pageWasChanged);

    boolean initialized();

    void initialized(boolean initialized);

    void updatePageSize(@NotNull ContextRender context);

    @NotNull
    CompletableFuture<?> loadCurrentPage(@NotNull ContextRender context, boolean forced);

    @NotNull
    Collection<Element> modifiableElements();

    void clearElements();
}
