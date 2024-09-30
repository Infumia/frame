package net.infumia.inv.api;

import net.infumia.inv.api.util.Reflection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class Internal {

    private static final String DEFAULT_FACTORY_CLASS =
        "net.infumia.frame.InventoryManagerFactoryImpl";

    @Nullable
    private static InventoryManagerFactory factory;

    @NotNull
    static InventoryManagerFactory factory() {
        if (Internal.factory != null) {
            return Internal.factory;
        }
        final InventoryManagerFactory found = Reflection.findInstanceFromField(
            Internal.DEFAULT_FACTORY_CLASS,
            "INSTANCEC"
        );
        return Internal.factory = found;
    }

    private Internal() {
        throw new IllegalStateException("Utility class!");
    }
}
