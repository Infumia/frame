package net.infumia.frame.context;

import net.infumia.frame.Frame;
import net.infumia.frame.typedkey.TypedKeyStorage;
import org.jetbrains.annotations.NotNull;

public interface Context {
    @NotNull
    Frame frame();

    @NotNull
    TypedKeyStorage instances();
}
