package net.infumia.frame.view;

import net.infumia.frame.InvTypeRich;
import org.jetbrains.annotations.NotNull;

public interface ViewContainerRich extends ViewContainer {
    @NotNull
    InvTypeRich typeRich();
}
