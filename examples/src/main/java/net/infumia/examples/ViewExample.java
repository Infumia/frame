package net.infumia.examples;

import net.infumia.frame.context.ContextBase;
import net.infumia.frame.context.view.ContextInit;
import net.infumia.frame.context.view.ContextOpen;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.element.pagination.ElementPagination;
import net.infumia.frame.state.pagination.StatePagination;
import net.infumia.frame.type.InvType;
import net.infumia.frame.view.ViewHandler;
import net.infumia.frame.view.config.ViewConfigBuilder;
import net.infumia.frame.viewer.Viewer;
import net.infumia.titleupdater.TitleUpdater;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ViewExample implements ViewHandler {

    private static final ViewExample INSTANCE = new ViewExample();

    private static final String TITLE = "Player: %s | %s/%s";

    private StatePagination pagination;

    private ViewExample() {}

    @Override
    public void onInit(@NotNull final ContextInit ctx) {
        ctx.configBuilder().type(InvType.CHEST).cancelOnClick();
        this.pagination = ctx
            .buildLazyPaginationState(() -> List.of("test-1", "test-2", "test-3", "test-4"))
            .elementConfigurer((builder, text) -> {
                final ItemStack item = new ItemStack(Material.CHEST);
                item.editMeta(meta -> meta.displayName(Component.text(text)));
                builder.item(item);
            })
            .layout('a')
            .onPageSwitch((context, ___) ->
                TitleUpdater.update(context.viewer().player(), this.generateTitle(context))
            )
            .buildPagination();
    }

    @Override
    public void onOpen(@NotNull final ContextOpen ctx) {
        final ViewConfigBuilder config = ctx.modifyConfig();
        config
            .layout(new String[]{"xxxxxxxxx", "xxxxaxxxx", "xxxbxcxxx"})
            .title(this.generateTitle(ctx));
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

    @NotNull
    private String generateTitle(final ContextBase ctx) {
        final Viewer viewer = ctx.viewer();
        final ElementPagination pagination = this.pagination.getOrThrow(ctx);
        return String.format(ViewExample.TITLE,
            viewer.player().getName(), pagination.currentPageIndex() + 1, pagination.pageCount());
    }
}
