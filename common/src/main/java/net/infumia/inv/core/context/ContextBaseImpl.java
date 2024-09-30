package net.infumia.inv.core.context;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.infumia.inv.api.context.ContextBase;
import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.state.value.StateValueHost;
import net.infumia.inv.api.typedkey.TypedKeyStorage;
import net.infumia.inv.api.typedkey.TypedKeyStorageImmutable;
import net.infumia.inv.api.typedkey.TypedKeyStorageImmutableBuilder;
import net.infumia.inv.api.util.Preconditions;
import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.core.InventoryManagerRich;
import net.infumia.inv.core.config.ViewConfigRich;
import net.infumia.inv.core.state.StateRegistry;
import net.infumia.inv.core.state.value.StateValueHostImpl;
import net.infumia.inv.core.view.ViewRich;
import net.infumia.inv.core.viewer.ViewerRich;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ContextBaseImpl extends ContextImpl implements ContextBaseRich {

    private final UUID id;
    private final StateValueHost stateValueHost;
    private final ViewRich view;
    private final ViewConfigRich initialConfig;
    private final Collection<Viewer> viewers;
    private final TypedKeyStorageImmutable initialData;
    private final ViewerRich singleViewer;

    public ContextBaseImpl(
        @NotNull final InventoryManagerRich manager,
        @NotNull final TypedKeyStorage instances,
        @NotNull final StateRegistry stateRegistry,
        @NotNull final UUID id,
        @NotNull final ViewRich view,
        @NotNull final ViewConfigRich initialConfig,
        @NotNull final Collection<Viewer> viewers,
        @NotNull final TypedKeyStorageImmutable initialData,
        @Nullable final ViewerRich singleViewer
    ) {
        super(manager, instances, stateRegistry);
        this.id = id;
        this.view = view;
        this.initialConfig = initialConfig;
        this.viewers = new HashSet<>(viewers);
        this.initialData = initialData;
        this.singleViewer = singleViewer;
        this.stateValueHost = new StateValueHostImpl(this);
    }

    public ContextBaseImpl(@NotNull final ContextBaseRich context) {
        super(context.manager(), context.instances(), context.stateRegistry());
        this.id = context.id();
        this.view = context.view();
        this.initialConfig = context.initialConfig();
        this.viewers = new HashSet<>(context.viewers());
        this.initialData = context.initialData();
        this.singleViewer = context.viewer();
        this.stateValueHost = context.stateValueHost();
    }

    @NotNull
    @Override
    public StateValueHost stateValueHost() {
        return this.stateValueHost;
    }

    @NotNull
    @Override
    public UUID id() {
        return this.id;
    }

    @NotNull
    @Override
    public Collection<Viewer> viewers() {
        synchronized (this.viewers) {
            return Collections.unmodifiableCollection(this.viewers);
        }
    }

    @Override
    public boolean sharedView() {
        return this.singleViewer == null || this.viewers().size() > 1;
    }

    @NotNull
    @Override
    public CompletableFuture<@Nullable ContextRender> openForEveryone(
        @NotNull final Class<?> viewClass
    ) {
        return this.manager()
            .open(
                this.viewers().stream().map(Viewer::player).collect(Collectors.toSet()),
                viewClass
            );
    }

    @NotNull
    @Override
    public CompletableFuture<@Nullable ContextRender> openForEveryone(
        @NotNull final Class<?> viewClass,
        @NotNull final Consumer<TypedKeyStorageImmutableBuilder> initialDataConfigurer
    ) {
        return this.manager()
            .open(
                this.viewers().stream().map(Viewer::player).collect(Collectors.toSet()),
                viewClass,
                initialDataConfigurer
            );
    }

    @NotNull
    @Override
    public CompletableFuture<@Nullable ContextRender> openForViewer(
        @NotNull final Class<?> viewClass
    ) {
        return this.manager().open(this.viewerOrThrow("openForViewer").player(), viewClass);
    }

    @NotNull
    @Override
    public CompletableFuture<@Nullable ContextRender> openForViewer(
        @NotNull final Class<?> viewClass,
        @NotNull final Consumer<TypedKeyStorageImmutableBuilder> initialData
    ) {
        return this.manager()
            .open(this.viewerOrThrow("openForViewer").player(), viewClass, initialData);
    }

    @NotNull
    @Override
    public ViewRich view() {
        return this.view;
    }

    @NotNull
    @Override
    public ViewConfigRich initialConfig() {
        return this.initialConfig;
    }

    @NotNull
    @Override
    public ViewerRich viewer() {
        return this.viewerOrThrow("viewer");
    }

    @Override
    public void addViewer(@NotNull final ViewerRich viewer) {
        synchronized (this.viewers) {
            this.viewers.add(viewer);
        }
    }

    @Override
    public void removeViewer(@NotNull final ViewerRich viewer) {
        synchronized (this.viewers) {
            this.viewers.remove(viewer);
        }
    }

    @NotNull
    @Override
    public ViewerRich viewerOrThrow(@NotNull final String methodName) {
        Preconditions.state(
            !this.sharedView(),
            "You cannot use #%s() method if it's a shared view!",
            methodName
        );
        return Preconditions.argumentNotNull(
            this.singleViewer,
            "Viewer not set even tough it's not a shared view!"
        );
    }

    @NotNull
    @Override
    public TypedKeyStorageImmutable initialData() {
        return this.initialData;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContextBase)) {
            return false;
        }
        return Objects.equals(this.id, ((ContextBase) o).id());
    }
}
