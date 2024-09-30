package net.infumia.frame.context;

import net.infumia.frame.Frame;
import net.infumia.frame.state.StateFactoryImpl;
import net.infumia.frame.state.StateRegistry;
import net.infumia.frame.typedkey.TypedKeyStorage;
import org.jetbrains.annotations.NotNull;

public class ContextImpl extends StateFactoryImpl implements ContextRich {

    private final Frame frame;
    private final TypedKeyStorage instances;
    private final StateRegistry stateRegistry;

    public ContextImpl(
        @NotNull final Frame frame,
        @NotNull final TypedKeyStorage instances,
        @NotNull final StateRegistry stateRegistry
    ) {
        super(stateRegistry);
        this.frame = frame;
        this.instances = instances;
        this.stateRegistry = stateRegistry;
    }

    public ContextImpl(@NotNull final Context context) {
        this(context.frame(), context.instances(), ((ContextRich) context).stateRegistry());
    }

    @NotNull
    @Override
    public Frame frame() {
        return this.frame;
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
