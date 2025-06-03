package net.infumia.frame.context;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.infumia.frame.Preconditions;
import net.infumia.frame.context.view.ContextInit;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.state.value.StateValueHost;
import net.infumia.frame.state.value.StateValueHostImpl;
import net.infumia.frame.typedkey.TypedKeyStorageImmutable;
import net.infumia.frame.typedkey.TypedKeyStorageImmutableBuilder;
import net.infumia.frame.view.View;
import net.infumia.frame.view.config.ViewConfig;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ContextBaseImpl extends ContextImpl implements ContextBaseRich {

    private final UUID id;
    private final StateValueHost stateValueHost;
    private final View view;
    private final ViewConfig initialConfig;
    private final Collection<Viewer> viewers;
    private final TypedKeyStorageImmutable initialData;
    private final Viewer singleViewer;

    public ContextBaseImpl(
        @NotNull final ContextInit context,
        @NotNull final UUID id,
        @NotNull final View view,
        @NotNull final ViewConfig initialConfig,
        @NotNull final Collection<Viewer> viewers,
        @NotNull final TypedKeyStorageImmutable initialData,
        @Nullable final Viewer singleViewer
    ) {
        super(context);
        this.id = id;
        this.view = view;
        this.initialConfig = initialConfig;
        this.viewers = new HashSet<>(viewers);
        this.initialData = initialData;
        this.singleViewer = singleViewer;
        this.stateValueHost = new StateValueHostImpl(this);
    }

    public ContextBaseImpl(@NotNull final ContextBase context) {
        super(context);
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
    public View view() {
        return this.view;
    }

    @NotNull
    @Override
    public ViewConfig initialConfig() {
        return this.initialConfig;
    }

    @NotNull
    @Override
    public TypedKeyStorageImmutable initialData() {
        return this.initialData;
    }

    @NotNull
    @Override
    public Collection<Viewer> viewers() {
        synchronized (this.viewers) {
            return Collections.unmodifiableCollection(this.viewers);
        }
    }

    @NotNull
    @Override
    public Viewer viewer() {
        return this.viewerOrThrow("viewer");
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
        return this.frame()
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
        return this.frame()
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
        return this.frame().open(this.viewerOrThrow("openForViewer").player(), viewClass);
    }

    @NotNull
    @Override
    public CompletableFuture<@Nullable ContextRender> openForViewer(
        @NotNull final Class<?> viewClass,
        @NotNull final Consumer<TypedKeyStorageImmutableBuilder> initialData
    ) {
        return this.frame()
            .open(this.viewerOrThrow("openForViewer").player(), viewClass, initialData);
    }

    @Override
    public void addViewer(@NotNull final Viewer viewer) {
        synchronized (this.viewers) {
            this.viewers.add(viewer);
        }
    }

    @Override
    public void removeViewer(@NotNull final Viewer viewer) {
        synchronized (this.viewers) {
            this.viewers.remove(viewer);
        }
    }

    @NotNull
    @Override
    public Viewer viewerOrThrow(@NotNull final String methodName) {
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
