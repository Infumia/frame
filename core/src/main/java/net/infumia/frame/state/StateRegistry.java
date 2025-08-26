package net.infumia.frame.state;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.infumia.frame.logger.Logger;
import org.jetbrains.annotations.NotNull;

public final class StateRegistry implements Iterable<StateRich<Object>> {

    private final Map<Long, StateRich<Object>> states = new HashMap<>();
    private final Logger logger;

    public StateRegistry(@NotNull final Logger logger) {
        this.logger = logger;
    }

    @SuppressWarnings("unchecked")
    public void register(@NotNull final StateRich<?> state) {
        this.states.put(state.id(), (StateRich<Object>) state);
        this.logger.debug("State '%s:%s' registered", state.id(), state);
    }

    public int size() {
        return this.states.size();
    }

    @NotNull
    @Override
    public Iterator<StateRich<Object>> iterator() {
        return Collections.unmodifiableCollection(this.states.values()).iterator();
    }
}
