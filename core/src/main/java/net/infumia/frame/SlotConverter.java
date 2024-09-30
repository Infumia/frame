package net.infumia.frame;

import net.infumia.frame.util.Preconditions;

public final class SlotConverter {

    public static int convertSlot(
        final int row,
        final int column,
        final int maxRowsCount,
        final int maxColumnsCount
    ) {
        Preconditions.argument(
            maxRowsCount >= row,
            "Row cannot be greater than %d (given %d)",
            maxRowsCount,
            row
        );
        Preconditions.argument(
            maxColumnsCount >= column,
            "Column cannot be greater than %d (given %d)",
            maxColumnsCount,
            column
        );
        return Math.max(row - 1, 0) * maxColumnsCount + Math.max(column - 1, 0);
    }

    private SlotConverter() {
        throw new IllegalStateException("Utility class!");
    }
}
