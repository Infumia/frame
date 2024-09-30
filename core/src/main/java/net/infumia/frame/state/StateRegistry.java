package net.infumia.frame.state;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.infumia.frame.logger.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class StateRegistry implements Iterable<StateRich<Object>> {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Map<Long, StateRich<Object>> states = new HashMap<>();
    private final Logger logger;

    public StateRegistry(@NotNull final Logger logger) {
        this.logger = logger;
    }

    @Nullable
    public StateRich<?> byId(final long internalId) {
        try {
            this.lock.readLock().lock();
            return this.states.get(internalId);
        } finally {
            this.lock.readLock().unlock();
        }
    }

    @SuppressWarnings("unchecked")
    public void register(@NotNull final StateRich<?> state) {
        try {
            this.lock.writeLock().lock();
            this.states.put(state.id(), (StateRich<Object>) state);
            this.logger.debug("State '%s:%s' registered", state.id(), state);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public void unregister(@NotNull final StateRich<?> state) {
        try {
            this.lock.writeLock().lock();
            this.states.remove(state.id());
            this.logger.debug("State '%s:%s' unregistered", state.id(), state);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @NotNull
    @Override
    public Iterator<StateRich<Object>> iterator() {
        try {
            this.lock.readLock().lock();
            return Collections.unmodifiableCollection(this.states.values()).iterator();
        } finally {
            this.lock.readLock().unlock();
        }
    }
}
