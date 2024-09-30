package net.infumia.frame.context;

import net.infumia.frame.Frame;
import net.infumia.frame.state.StateFactoryImpl;
import net.infumia.frame.state.StateRegistry;
import net.infumia.frame.typedkey.TypedKeyStorage;
import org.jetbrains.annotations.NotNull;

public class ContextImpl extends StateFactoryImpl implements ContextRich {

    private final Frame manager;
    private final TypedKeyStorage instances;
    private final StateRegistry stateRegistry;

    public ContextImpl(
        @NotNull final Frame manager,
        @NotNull final TypedKeyStorage instances,
        @NotNull final StateRegistry stateRegistry
    ) {
        super(stateRegistry);
        this.manager = manager;
        this.instances = instances;
        this.stateRegistry = stateRegistry;
    }

    public ContextImpl(@NotNull final Context context) {
        this(context.manager(), context.instances(), ((ContextRich) context).stateRegistry());
    }

    @NotNull
    @Override
    public Frame manager() {
        return this.manager;
    }

    @NotNull
    @Override
    public TypedKeyStorage instances() {
        return this.instances;
    }

    @NotNull
    @Override
    public StateRegistry stateRegistry() {
        return this.stateRegistry;
    }
}
