package net.infumia.examples;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.view.ContextInit;
import net.infumia.frame.context.view.ContextOpen;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.state.pagination.StatePagination;
import net.infumia.frame.type.InvType;
import net.infumia.frame.view.ViewHandler;
import net.infumia.frame.viewer.Viewer;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public final class ViewExample implements ViewHandler {

    private StatePagination pagination;

    @Override
    public void onInit(final ContextInit ctx) {
        ctx.configBuilder().type(InvType.CHEST).cancelOnClick();
        this.pagination = ctx
            .buildLazyAsyncPaginationState(() ->
                CompletableFuture.supplyAsync(() -> List.of("test-1", "test-2"))
            )
            .elementConfigurer((builder, text) -> {
                final ItemStack item = new ItemStack(Material.CHEST);
                item.editMeta(meta -> meta.displayName(Component.text(text)));
                builder.item(item);
            })
            .layout('a')
            .buildPagination();
    }

    @Override
    public void onOpen(final ContextOpen ctx) {
        final Viewer viewer = ctx.viewer();
        ctx
            .modifyConfig()
            .layout(new String[] { "xxxxxxxxx", "xxxaaaxxx", "xxxxxxxxx" })
            .title("Player: " + viewer.player().getName());
    }

    @Override
    public void onFirstRender(final ContextRender ctx) {
        final CommandSender sender = ctx.instances().getOrThrow(ExamplePlugin.CONSOLE_KEY);
        ctx.layoutSlot('x', new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

        ctx
            .layoutSlot('a', new ItemStack(Material.DIAMOND))
            .onClick(context -> {
                context.closeForViewer();
                sender.sendMessage(
                    "Player " + context.clicker().player() + " clicked to a diamond!"
                );
            });
    }
}
