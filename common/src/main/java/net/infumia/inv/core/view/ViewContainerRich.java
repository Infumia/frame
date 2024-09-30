package net.infumia.inv.core.view;

import net.infumia.inv.api.view.ViewContainer;
import net.infumia.inv.core.InvTypeRich;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ViewContainerRich extends ViewContainer {
    @NotNull
    InvTypeRich typeRich();

    @Override
    @Nullable
    ViewContainerRich at(int slot);
}
