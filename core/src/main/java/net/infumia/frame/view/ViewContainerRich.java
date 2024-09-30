package net.infumia.frame.view;

import net.infumia.frame.InvTypeRich;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ViewContainerRich extends ViewContainer {
    @NotNull
    InvTypeRich typeRich();

    @Override
    @Nullable
    ViewContainerRich at(int slot);
}
