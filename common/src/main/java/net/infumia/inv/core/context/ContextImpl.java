package net.infumia.inv.core.context;

import net.infumia.inv.api.state.StateFactory;
import net.infumia.inv.api.typedkey.TypedKeyStorage;
import net.infumia.inv.core.InventoryManagerRich;
import net.infumia.inv.core.state.StateFactoryImpl;
import net.infumia.inv.core.state.StateRegistry;
import org.jetbrains.annotations.NotNull;

public class ContextImpl implements ContextRich {

    private final InventoryManagerRich manager;
    private final TypedKeyStorage instances;
    private final StateRegistry stateRegistry;
    private final StateFactory stateFactory;

    protected ContextImpl(
        @NotNull final InventoryManagerRich manager,
        @NotNull final TypedKeyStorage instances,
        @NotNull final StateRegistry stateRegistry
    ) {
        this.manager = manager;
        this.instances = instances;
        this.stateRegistry = stateRegistry;
        this.stateFactory = new StateFactoryImpl(stateRegistry);
    }

    @NotNull
    @Override
    public InventoryManagerRich manager() {
        return this.manager;
    }

    @NotNull
    @Override
    public StateRegistry stateRegistry() {
        return this.stateRegistry;
    }

    @NotNull
    @Override
    public TypedKeyStorage instances() {
        return this.instances;
    }

    @NotNull
    @Override
    public StateFactory stateFactory() {
        return this.stateFactory;
    }
}
