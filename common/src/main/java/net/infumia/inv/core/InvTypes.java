package net.infumia.inv.core;

import net.infumia.inv.api.type.InvType;
import org.jetbrains.annotations.NotNull;

public final class InvTypes {

    public static final InvTypeRich CHEST = new InvTypeRich(InvType.CHEST, 54, 6, 9, true);
    public static final InvTypeRich PLAYER = new InvTypeRich(InvType.PLAYER, 36, 3, 9);
    public static final InvTypeRich DISPENSER = new InvTypeRich(InvType.DISPENSER, 9, 3, 3);
    public static final InvTypeRich DROPPER = new InvTypeRich(InvType.DROPPER, 9, 3, 3);
    public static final InvTypeRich FURNACE = new InvTypeRich(
        InvType.FURNACE,
        3,
        2,
        2,
        false,
        new int[] { 2 }
    );
    public static final InvTypeRich HOPPER = new InvTypeRich(InvType.HOPPER, 5, 1, 5);
    public static final InvTypeRich BLAST_FURNACE = new InvTypeRich(
        InvType.BLAST_FURNACE,
        3,
        2,
        2,
        false,
        new int[] { 2 }
    );
    public static final InvTypeRich WORKBENCH = new InvTypeRich(
        InvType.WORKBENCH,
        9,
        3,
        3,
        false,
        new int[] { 3 }
    );
    public static final InvTypeRich BREWING_STAND = new InvTypeRich(
        InvType.BREWING_STAND,
        4,
        1,
        1,
        false,
        new int[] { 0, 1, 2 },
        false
    );
    public static final InvTypeRich BEACON = new InvTypeRich(InvType.BEACON, 1, 1, 1);
    public static final InvTypeRich ANVIL = new InvTypeRich(
        InvType.ANVIL,
        3,
        1,
        3,
        false,
        new int[] { 2 }
    );
    public static final InvTypeRich SHULKER_BOX = new InvTypeRich(InvType.SHULKER_BOX, 27, 3, 9);
    public static final InvTypeRich SMOKER = new InvTypeRich(
        InvType.SMOKER,
        3,
        2,
        2,
        false,
        new int[] { 2 }
    );
    public static final InvTypeRich MERCHANT = new InvTypeRich(
        InvType.MERCHANT,
        3,
        1,
        3,
        false,
        new int[] { 2 }
    );

    @NotNull
    public static InvTypeRich fromType(@NotNull final InvType type) {
        switch (type) {
            case CHEST:
                return InvTypes.CHEST;
            case PLAYER:
                return InvTypes.PLAYER;
            case DISPENSER:
                return InvTypes.DISPENSER;
            case DROPPER:
                return InvTypes.DROPPER;
            case FURNACE:
                return InvTypes.FURNACE;
            case HOPPER:
                return InvTypes.HOPPER;
            case BLAST_FURNACE:
                return InvTypes.BLAST_FURNACE;
            case WORKBENCH:
                return InvTypes.WORKBENCH;
            case BREWING_STAND:
                return InvTypes.BREWING_STAND;
            case BEACON:
                return InvTypes.BEACON;
            case ANVIL:
                return InvTypes.ANVIL;
            case SHULKER_BOX:
                return InvTypes.SHULKER_BOX;
            case SMOKER:
                return InvTypes.SMOKER;
            case MERCHANT:
                return InvTypes.MERCHANT;
            default:
                throw new IllegalArgumentException("Invalid type: " + type);
        }
    }

    private InvTypes() {
        throw new IllegalAccessError("Utility class!");
    }
}
