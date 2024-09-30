package net.infumia.frame.state;

import net.infumia.frame.element.pagination.ElementPagination;
import net.infumia.frame.state.value.StateValueFactory;
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
