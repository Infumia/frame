package net.infumia.frame;

import java.util.Arrays;
import net.infumia.frame.type.InvType;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class InvTypeRich {

    private static final int[] EMPTY_RESULT_SLOTS = new int[0];

    private final InvType type;
    private final int maxSize;
    private final int rows;
    private final int columns;
    private final boolean extendable;
    private final int@NotNull [] resultSlots;
    private final boolean aligned;

    InvTypeRich(
        @NotNull final InvType type,
        final int maxSize,
        final int rows,
        final int columns,
        final boolean extendable,
        final int@NotNull [] resultSlots,
        final boolean aligned
    ) {
        this.type = type;
        this.maxSize = maxSize;
        this.rows = rows;
        this.columns = columns;
        this.extendable = extendable;
        this.resultSlots = resultSlots;
        this.aligned = aligned;
    }

    InvTypeRich(
        @NotNull final InvType type,
        final int maxSize,
        final int rows,
        final int columns,
        final boolean extendable,
        final int[] resultSlots
    ) {
        this(type, maxSize, rows, columns, extendable, resultSlots, true);
    }

    InvTypeRich(
        @NotNull final InvType type,
        final int maxSize,
        final int rows,
        final int columns,
        final boolean extendable
    ) {
        this(type, maxSize, rows, columns, extendable, InvTypeRich.EMPTY_RESULT_SLOTS);
    }

    InvTypeRich(@NotNull final InvType type, final int maxSize, final int rows, final int columns) {
        this(type, maxSize, rows, columns, false);
    }

    @NotNull
    public InvType type() {
        return this.type;
    }

    public int maxSize() {
        return this.maxSize;
    }

    public int rows() {
        return this.rows;
    }

    public int columns() {
        return this.columns;
    }

    public boolean extendable() {
        return this.extendable;
    }

    public int@NotNull [] resultSlots() {
        return this.resultSlots;
    }

    public boolean aligned() {
        return this.aligned;
    }

    public boolean isResultSlot(final int slot) {
        return Arrays.stream(this.resultSlots).anyMatch(resultSlot -> resultSlot == slot);
    }

    public boolean canPlayerInteractOn(final int slot) {
        return Arrays.stream(this.resultSlots).noneMatch(resultSlot -> resultSlot == slot);
    }

    public int normalize(final int size) {
        if (size == 0) {
            return size;
        }
        final int fullSize = this.fullSize(size);
        Preconditions.argument(
            fullSize <= this.maxSize,
            "Size cannot exceed container max size of %d (given: %d (%s rows))",
            this.maxSize,
            fullSize,
            size
        );
        return fullSize;
    }

    @Nullable
    public InventoryType toInventoryType() {
        switch (this.type) {
            case CHEST:
                return InventoryType.CHEST;
            case PLAYER:
                return InventoryType.PLAYER;
            default:
                return null;
        }
    }

    private int fullSize(final int size) {
        if (size <= this.rows) {
            return size * this.columns;
        }
        if (size == Integer.MAX_VALUE) {
            return this.maxSize;
        }
        Preconditions.argument(
            size % this.columns == 0,
            "Container size must be a multiple of %d (given: %d)",
            this.columns,
            size
        );
        return size;
    }
}
