package net.infumia.frame.context;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.state.value.StateValueHostHolder;
import net.infumia.frame.typedkey.TypedKeyStorageImmutable;
import net.infumia.frame.typedkey.TypedKeyStorageImmutableBuilder;
import net.infumia.frame.view.View;
import net.infumia.frame.view.config.ViewConfig;
import net.infumia.frame.viewer.Viewer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ContextBase extends Context, StateValueHostHolder {
    @NotNull
    UUID id();

    @NotNull
    View view();

    @NotNull
    ViewConfig initialConfig();

    @NotNull
    TypedKeyStorageImmutable initialData();

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
