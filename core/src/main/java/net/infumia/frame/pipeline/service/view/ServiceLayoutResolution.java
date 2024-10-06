package net.infumia.frame.pipeline.service.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.util.Preconditions;
import net.infumia.frame.view.ViewContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ServiceLayoutResolution
    implements PipelineServiceConsumer<PipelineContextView.LayoutResolution> {

    public static final PipelineServiceConsumer<PipelineContextView.LayoutResolution> INSTANCE =
        new ServiceLayoutResolution();

    public static final String KEY = "layout-resolution";

    @NotNull
    @Override
    public String key() {
        return ServiceLayoutResolution.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.LayoutResolution ctx) {
        final String@Nullable[] layout = ctx.config().layout();
        if (layout == null) {
            return;
        }
        final int layoutRows = layout.length;
        if (layoutRows == 0) {
            return;
        }
        final ViewContainer container = ctx.container();
        final int containerRows = container.rowsCount();
        Preconditions.state(
            containerRows == layoutRows,
            "Layout length (%d) must respect the rows count of the container (%d).",
            layoutRows,
            containerRows
        );
        final Map<Character, Collection<Integer>> layouts = new HashMap<>();
        final int containerColumns = container.columnsCount();
        for (int row = 0; row < containerRows; row++) {
            final String layer = layout[row];
            final int layerLength = layer.length();
            Preconditions.state(
                containerColumns == layerLength,
                "Layout layer length located at %d must respect the columns count of the container (given: %d, expect: %d).",
                row,
                layerLength,
                containerColumns
            );
            for (int column = 0; column < containerColumns; column++) {
                layouts
                    .computeIfAbsent(layer.charAt(column), ArrayList::new)
                    .add(column + row * containerColumns);
            }
        }
        layouts.forEach(ctx::addLayout);
    }

    private ServiceLayoutResolution() {}
}
