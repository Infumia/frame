package net.infumia.frame.context;

import net.infumia.frame.Frame;
import net.infumia.frame.state.StateFactory;
import net.infumia.frame.typedkey.TypedKeyStorage;
import org.jetbrains.annotations.NotNull;

public interface Context extends StateFactory {
    @NotNull
    Frame manager();

    @NotNull
    TypedKeyStorage instances();
}
