package net.infumia.inv.core.pipeline.service.view;

import net.infumia.inv.api.context.view.ContextClose;
import net.infumia.inv.api.pipeline.PipelineServiceConsumer;
import net.infumia.inv.api.pipeline.context.PipelineContextView;
import net.infumia.inv.api.viewer.Viewer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class ServiceCloseCancel
    implements PipelineServiceConsumer<PipelineContextView.Close> {

    public static final PipelineServiceConsumer<PipelineContextView.Close> INSTANCE =
        new ServiceCloseCancel();

    public static final String KEY = "cancel";

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
        context.manager().taskFactory().sync(() -> viewer.open(context.container()));
        final Player player = viewer.player();
        final ItemStack cursor = player.getItemOnCursor();
        if (cursor != null && cursor.getType() != Material.AIR) {
            player.setItemOnCursor(cursor);
        }
    }

    private ServiceCloseCancel() {}
}
