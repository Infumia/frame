package net.infumia.frame;

import net.infumia.frame.util.Reflection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class Internal {

    private static final String DEFAULT_FACTORY_CLASS =
        "net.infumia.frame.FrameFactoryImpl";

    @Nullable
    private static FrameFactory factory;

    @NotNull
    static FrameFactory factory() {
        if (Internal.factory != null) {
            return Internal.factory;
        }
        final FrameFactory found = Reflection.findInstanceFromField(
            Internal.DEFAULT_FACTORY_CLASS,
            "INSTANCE"
        );
        return Internal.factory = found;
    }

    private Internal() {
        throw new IllegalStateException("Utility class!");
    }
}
