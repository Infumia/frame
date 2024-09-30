package net.infumia.frame;

import net.infumia.frame.listener.InventoryListener;
import org.jetbrains.annotations.NotNull;

public interface FrameRich extends Frame {
    @NotNull
    InventoryListener listener();
}
