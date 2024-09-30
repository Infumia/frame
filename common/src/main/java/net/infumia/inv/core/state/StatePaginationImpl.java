package net.infumia.inv.core.state;

import net.infumia.inv.api.element.pagination.ElementPagination;
import net.infumia.inv.core.state.value.StateValueFactory;
import org.jetbrains.annotations.NotNull;

public final class StatePaginationImpl
    extends StateImpl<ElementPagination>
    implements StatePaginationRich {

    public StatePaginationImpl(
        final long id,
        @NotNull final StateValueFactory<ElementPagination> valueFactory
    ) {
        super(id, valueFactory);
    }
}
