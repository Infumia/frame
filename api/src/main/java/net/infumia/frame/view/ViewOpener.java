package net.infumia.frame.view;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.typedkey.TypedKeyStorageImmutableBuilder;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ViewOpener {
    @NotNull
    CompletableFuture<@Nullable ContextRender> open(
        @NotNull Player player,
        @NotNull Class<?> viewClass
    );

    @NotNull
    CompletableFuture<@Nullable ContextRender> open(
        @NotNull Player player,
        @NotNull Class<?> viewClass,
        @NotNull Consumer<TypedKeyStorageImmutableBuilder> initialDataConfigurer
    );

    @NotNull
    CompletableFuture<@Nullable ContextRender> open(
        @NotNull Collection<Player> players,
        @NotNull Class<?> viewClass
    );

    @NotNull
    CompletableFuture<@Nullable ContextRender> open(
        @NotNull Collection<Player> players,
        @NotNull Class<?> viewClass,
        @NotNull Consumer<TypedKeyStorageImmutableBuilder> initialDataConfigurer
    );

    @NotNull
    CompletableFuture<ContextRender> openActive(
        @NotNull Player player,
        @NotNull ContextRender activeContext
    );

    @NotNull
    CompletableFuture<ContextRender> openActive(
        @NotNull Collection<Player> players,
        @NotNull ContextRender activeContext
    );
}
