package net.infumia.examples;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.context.view.ContextInit;
import net.infumia.frame.context.view.ContextOpen;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.element.pagination.ElementPagination;
import net.infumia.frame.state.pagination.StatePagination;
import net.infumia.frame.type.InvType;
import net.infumia.frame.view.ViewHandler;
import net.infumia.frame.viewer.Viewer;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class ViewExample implements ViewHandler {

    private StatePagination pagination;

    @Override
    public void onInit(@NotNull final ContextInit ctx) {
        ctx.configBuilder().type(InvType.CHEST).cancelOnClick();
        this.pagination = ctx
            .buildComputedAsyncPaginationState(() ->
                CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(5000L);
                    } catch (final InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return List.of("test-1", "test-2", "test-3", "test-4");
                })
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
    public void onOpen(@NotNull final ContextOpen ctx) {
        final Viewer viewer = ctx.viewer();
        ctx
            .modifyConfig()
            .layout(new String[] { "xxxxxxxxx", "xxxxaxxxx", "xxxbxcxxx" })
            .title("Player: " + viewer.player().getName());
    }

    @Override
    public void onFirstRender(@NotNull final ContextRender ctx) {
        final ElementPagination pagination = this.pagination.getOrThrow(ctx);
        ctx.layoutSlot('x', new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        ctx
            .layoutSlot('b', new ItemStack(Material.ARROW))
            .onClick(click -> pagination.back())
            .updateOnStateChange(this.pagination)
            .displayIf(pagination::canBack);
        ctx
            .layoutSlot('c', new ItemStack(Material.ARROW))
            .onClick(click -> pagination.advance())
            .updateOnStateChange(this.pagination)
            .displayIf(pagination::canAdvance);
    }
}
