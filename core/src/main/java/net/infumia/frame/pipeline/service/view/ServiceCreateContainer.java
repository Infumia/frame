package net.infumia.frame.pipeline.service.view;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import net.infumia.frame.InvTypeRich;
import net.infumia.frame.InvTypes;
import net.infumia.frame.context.ContextBase;
import net.infumia.frame.pipeline.PipelineService;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.type.InvType;
import net.infumia.frame.util.Preconditions;
import net.infumia.frame.view.InventoryHolderView;
import net.infumia.frame.view.ViewContainer;
import net.infumia.frame.view.ViewContainerImpl;
import net.infumia.frame.view.config.ViewConfig;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public final class ServiceCreateContainer
    implements PipelineService<PipelineContextView.CreateContainer, ViewContainer> {

    private static final Collection<InvTypeRich> EXTENDABLE = Arrays.stream(InvType.VALUES)
        .map(InvTypes::fromType)
        .filter(InvTypeRich::extendable)
        .collect(Collectors.toSet());

    public static final PipelineService<
        PipelineContextView.CreateContainer,
        ViewContainer
    > INSTANCE = new ServiceCreateContainer();

    public static final String KEY = "create";

    @NotNull
    @Override
    public String key() {
        return ServiceCreateContainer.KEY;
    }

    @NotNull
    @Override
    public CompletableFuture<ViewContainer> handle(
        @NotNull final PipelineContextView.CreateContainer ctx
    ) {
        final ContextBase context = ctx.context();
        final Object instance = context.view().instance();
        final ViewConfig config = ctx.config();
        final InvType type = config.type();
        final InvTypeRich typeRich = InvTypes.fromType(type);
        final InventoryType inventoryType = typeRich.toInventoryType();
        Preconditions.argumentNotNull(inventoryType, "%s view type is not supported!", type);
        final int normalized = typeRich.normalize(config.size());
        Preconditions.argument(
            normalized == 0 || typeRich.extendable(),
            "Only '%s' type(s) can have a custom size, '%s' always have a size of %d. Remove the part that specifies the size of the container on %s or just set the type explicitly.",
            ServiceCreateContainer.EXTENDABLE.stream()
                .map(InvTypeRich::type)
                .collect(Collectors.toSet()),
            type,
            typeRich.maxSize(),
            instance
        );
        final InventoryHolderView holder = new InventoryHolderView();
        final Inventory inventory = context
            .frame()
            .inventoryCreator()
            .create(
                holder,
                inventoryType,
                normalized,
                config.title()
            );
        holder.inventory(inventory);
        return CompletableFuture.completedFuture(new ViewContainerImpl(holder, typeRich));
    }

    private ServiceCreateContainer() {}
}
