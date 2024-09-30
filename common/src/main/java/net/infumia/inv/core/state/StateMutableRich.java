package net.infumia.inv.core.state;

import net.infumia.inv.api.state.StateMutable;

public interface StateMutableRich<T> extends StateRich<T>, StateMutable<T> {}
