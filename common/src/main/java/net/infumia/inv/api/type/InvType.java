package net.infumia.inv.api.type;

import org.jetbrains.annotations.NotNull;

public enum InvType {
    CHEST,
    PLAYER,
    DISPENSER,
    DROPPER,
    FURNACE,
    HOPPER,
    BLAST_FURNACE,
    WORKBENCH,
    BREWING_STAND,
    BEACON,
    ANVIL,
    SHULKER_BOX,
    SMOKER,
    MERCHANT;

    @NotNull
    public static final InvType@NotNull[] VALUES = InvType.values();
}
