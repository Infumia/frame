package net.infumia.inv.core.element.pagination;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.element.pagination.ElementPagination;
import net.infumia.inv.core.element.ElementEventHandlerHolder;
import net.infumia.inv.core.element.ElementRich;
import net.infumia.inv.core.state.StateRich;
import org.jetbrains.annotations.NotNull;

public interface ElementPaginationRich<T>
    extends ElementRich, ElementPagination, ElementEventHandlerHolder {
    @NotNull
    StateRich<ElementPagination> associated();

    boolean pageWasChanged();

    void pageWasChanged(boolean pageWasChanged);

    boolean initialized();

    void initialized(boolean initialized);

    void updatePageSize(@NotNull ContextRender context);

    @NotNull
    CompletableFuture<?> loadCurrentPage(@NotNull ContextRender context);

    @NotNull
    Collection<ElementRich> modifiableElements();

    void clearElements();

    @NotNull
    @Override
    ElementPaginationBuilderRich<T> toBuilder();
}
