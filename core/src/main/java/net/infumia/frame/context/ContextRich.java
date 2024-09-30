package net.infumia.frame.context;

import net.infumia.frame.state.StateRegistry;
import org.jetbrains.annotations.NotNull;

public interface ContextRich extends Context {
    @NotNull
    StateRegistry stateRegistry();
}
