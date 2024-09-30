package net.infumia.inv.api.context;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.infumia.inv.api.InitialDataHolder;
import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.state.value.StateValueHostHolder;
import net.infumia.inv.api.typedkey.TypedKeyStorageImmutableBuilder;
import net.infumia.inv.api.view.View;
import net.infumia.inv.api.view.config.ViewConfig;
import net.infumia.inv.api.viewer.Viewer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ContextBase extends Context, StateValueHostHolder, InitialDataHolder {
    @NotNull
    UUID id();

    @NotNull
    View view();

    @NotNull
    ViewConfig initialConfig();

    @NotNull
    Collection<Viewer> viewers();

    @NotNull
    Viewer viewer();

    boolean sharedView();

    @NotNull
    CompletableFuture<@Nullable ContextRender> openForEveryone(@NotNull Class<?> viewClass);

    @NotNull
    CompletableFuture<@Nullable ContextRender> openForEveryone(
        @NotNull Class<?> viewClass,
        @NotNull Consumer<TypedKeyStorageImmutableBuilder> initialData
    );

    @NotNull
    CompletableFuture<@Nullable ContextRender> openForViewer(@NotNull Class<?> viewClass);

    @NotNull
    CompletableFuture<@Nullable ContextRender> openForViewer(
        @NotNull Class<?> viewClass,
        @NotNull Consumer<TypedKeyStorageImmutableBuilder> initialDataConfigurer
    );
}
