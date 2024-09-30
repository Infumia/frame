package net.infumia.inv.api.state;

import net.infumia.inv.api.util.Keyed;

public interface StateInitial<T> extends State<T>, Keyed<String> {}
