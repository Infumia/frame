package net.infumia.frame.pipeline.service.view;

import net.infumia.frame.context.view.ContextClose;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.viewer.Viewer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class ServiceCloseCancel
    implements PipelineServiceConsumer<PipelineContextView.Close> {

    public static final PipelineServiceConsumer<PipelineContextView.Close> INSTANCE =
        new ServiceCloseCancel();

    public static final String KEY = "cancel";

    @NotNull
    @Override
    public String key() {
        return ServiceCloseCancel.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.Close ctx) {
        final ContextClose context = ctx.context();
        final Viewer viewer = context.viewer();
        if (!context.viewer().player().isOnline()) {
            ctx.cancelled(true);
            return;
        }
        if (!context.cancelled() || context.forced()) {
            return;
        }
        ctx.cancelled(true);
        context.frame().taskFactory().sync(() -> context.container().open(viewer));
        final Player player = viewer.player();
        final ItemStack cursor = player.getItemOnCursor();
        if (cursor != null && cursor.getType() != Material.AIR) {
            player.setItemOnCursor(cursor);
        }
    }

    private ServiceCloseCancel() {}
}
