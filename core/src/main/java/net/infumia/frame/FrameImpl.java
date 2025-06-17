package net.infumia.frame;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.extension.CompletableFutureExtensions;
import net.infumia.frame.feature.Feature;
import net.infumia.frame.listener.InventoryListener;
import net.infumia.frame.logger.Logger;
import net.infumia.frame.metadata.MetadataAccessFactory;
import net.infumia.frame.metadata.MetadataAccessFactoryImpl;
import net.infumia.frame.pipeline.executor.PipelinesFrame;
import net.infumia.frame.pipeline.executor.PipelinesFrameImpl;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.task.TaskFactory;
import net.infumia.frame.task.TaskFactoryImpl;
import net.infumia.frame.typedkey.TypedKeyStorageFactory;
import net.infumia.frame.typedkey.TypedKeyStorageImmutableBuilder;
import net.infumia.frame.view.View;
import net.infumia.frame.view.ViewEventHandler;
import net.infumia.frame.view.ViewFactory;
import net.infumia.frame.view.ViewFactoryImpl;
import net.infumia.frame.view.creator.InventoryFactory;
import net.infumia.frame.view.creator.InventoryFactoryBukkit;
import net.infumia.frame.viewer.ViewerFactory;
import net.infumia.frame.viewer.ViewerFactoryImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class FrameImpl implements FrameRich {

    private final Collection<Class<?>> unregisteredViews = ConcurrentHashMap.newKeySet();
    private final Map<Class<?>, View> registeredViews = new ConcurrentHashMap<>();
    private final AtomicBoolean registered = new AtomicBoolean(false);
    private final PipelinesFrame pipelines = new PipelinesFrameImpl(this);
    private final ViewFactory viewFactory = new ViewFactoryImpl();
    private final Logger logger;
    private final TaskFactory taskFactory;
    private final InventoryListener listener;
    private final MetadataAccessFactory metadataAccessFactory;
    private final ViewerFactory viewerFactory;
    private TypedKeyStorageFactory storageFactory = TypedKeyStorageFactory.simple();
    private InventoryFactory inventoryFactory = InventoryFactoryBukkit.bukkitOrPaper();

    FrameImpl(
        @NotNull final Plugin plugin,
        @NotNull final Logger logger,
        final boolean unregisterOnDisable
    ) {
        this.logger = logger;
        this.taskFactory = new TaskFactoryImpl(plugin);
        this.metadataAccessFactory = new MetadataAccessFactoryImpl(plugin);
        this.viewerFactory = new ViewerFactoryImpl(this.metadataAccessFactory);
        this.listener = new InventoryListener(
            this,
            plugin,
            this.metadataAccessFactory,
            unregisterOnDisable
        );
    }

    @NotNull
    @Override
    public InventoryListener listener() {
        return this.listener;
    }

    @Override
    public void register() {
        this.register(__ -> {});
    }

    @NotNull
    @Override
    public CompletableFuture<ConsumerService.State> register(
        @NotNull final Consumer<TypedKeyStorageImmutableBuilder> instanceConfigurer
    ) {
        Preconditions.state(
            !this.registered.get(),
            "This frame is already registered! #register() method cannot be called twice!"
        );
        this.registered.set(true);
        return this.pipelines.executeCreateViews(this.unregisteredViews)
            .thenCompose(instances ->
                this.pipelines.executeRegisterViews(instances, instanceConfigurer)
            )
            .thenCompose(views -> {
                this.registeredViews.clear();
                this.registeredViews.putAll(
                        views
                            .stream()
                            .collect(
                                Collectors.toMap(
                                    view -> view.instance().getClass(),
                                    Function.identity()
                                )
                            )
                    );
                return this.pipelines.executeRegisterListeners();
            })
            .exceptionally(throwable -> {
                if (throwable instanceof CompletionException) {
                    throwable = throwable.getCause();
                }
                if (throwable != null) {
                    this.unregister();
                    this.logger.error(throwable, "Error occurred while registering views!");
                }
                return null;
            });
    }

    @Override
    public void unregister() {
        this.registered.set(false);
        this.metadataAccessFactory.clearCache(Bukkit.getOnlinePlayers());
        HandlerList.unregisterAll(this.listener);
        final HashMap<Class<?>, View> views = new HashMap<>(this.registeredViews);
        this.registeredViews.clear();
        this.loggedFuture(
                this.pipelines.executeUnregisterViews(views.values()),
                "Error occurred while unregistering views '%s'!",
                views.keySet()
            );
    }

    @NotNull
    @Override
    public Frame with(@NotNull final Class<?> viewClass) {
        Preconditions.argument(!this.registered.get(), "This frame is registered!");
        Preconditions.argument(
            !this.unregisteredViews.contains(viewClass),
            "View class '%s' already registered.",
            viewClass
        );
        this.logger.debug("View class '%s' recognized.", viewClass);
        this.unregisteredViews.add(viewClass);
        return this;
    }

    @NotNull
    @Override
    public Logger logger() {
        return this.logger;
    }

    @NotNull
    @Override
    public TaskFactory taskFactory() {
        return this.taskFactory;
    }

    @NotNull
    @Override
    public ViewFactory viewFactory() {
        return this.viewFactory;
    }

    @NotNull
    @Override
    public ViewerFactory viewerFactory() {
        return this.viewerFactory;
    }

    @NotNull
    @Override
    public TypedKeyStorageFactory storageFactory() {
        return this.storageFactory;
    }

    @Override
    public void storageFactory(@NotNull final TypedKeyStorageFactory storageFactory) {
        this.storageFactory = storageFactory;
    }

    @NotNull
    @Override
    public InventoryFactory inventoryFactory() {
        return this.inventoryFactory;
    }

    @Override
    public void inventoryFactory(@NotNull final InventoryFactory inventoryFactory) {
        this.inventoryFactory = inventoryFactory;
    }

    @NotNull
    @Override
    public <T> CompletableFuture<T> loggedFuture(
        @NotNull final CompletableFuture<T> future,
        @NotNull final String message,
        @NotNull final Object @NotNull... args
    ) {
        return CompletableFutureExtensions.logError(future, this.logger, message, args);
    }

    @NotNull
    @Override
    public CompletableFuture<@Nullable ContextRender> open(
        @NotNull final Player player,
        @NotNull final Class<?> viewClass
    ) {
        return this.open(Collections.singleton(player), viewClass);
    }

    @NotNull
    @Override
    public CompletableFuture<@Nullable ContextRender> open(
        @NotNull final Player player,
        @NotNull final Class<?> viewClass,
        @NotNull final Consumer<TypedKeyStorageImmutableBuilder> initialDataConfigurer
    ) {
        return this.open(Collections.singleton(player), viewClass, initialDataConfigurer);
    }

    @NotNull
    @Override
    public CompletableFuture<@Nullable ContextRender> open(
        @NotNull final Collection<Player> players,
        @NotNull final Class<?> viewClass
    ) {
        return this.open(players, viewClass, builder -> {});
    }

    @NotNull
    @Override
    public CompletableFuture<@Nullable ContextRender> open(
        @NotNull final Collection<Player> players,
        @NotNull final Class<?> viewClass,
        @NotNull final Consumer<TypedKeyStorageImmutableBuilder> initialDataConfigurer
    ) {
        Preconditions.state(
            this.registered.get(),
            "Before you open a view you must register this frame!"
        );
        final View view = Preconditions.argumentNotNull(
            this.registeredViews.get(viewClass),
            "View '%s' is not registered!",
            viewClass
        );

        if (!(view instanceof ViewEventHandler)) {
            return CompletableFuture.completedFuture(null);
        }
        final TypedKeyStorageImmutableBuilder builder =
            this.storageFactory.createImmutableBuilder(new HashMap<>());
        initialDataConfigurer.accept(builder);
        return this.loggedFuture(
                this.taskFactory.handleFuture(() ->
                        ((ViewEventHandler) view).simulateOpen(players, builder.build())
                    ),
                "Error occurred while opening view '%s'!",
                viewClass
            );
    }

    @NotNull
    @Override
    public CompletableFuture<@Nullable ContextRender> openActive(
        @NotNull final Player player,
        @NotNull final ContextRender activeContext
    ) {
        return this.openActive(Collections.singleton(player), activeContext);
    }

    @NotNull
    @Override
    public CompletableFuture<@Nullable ContextRender> openActive(
        @NotNull final Collection<Player> players,
        @NotNull final ContextRender activeContext
    ) {
        final View view = activeContext.view();
        Preconditions.argument(
            view instanceof ViewEventHandler,
            "The active context's view must be an instance of ViewEventHandler!"
        );
        return this.loggedFuture(
                this.taskFactory.handleFuture(() ->
                        ((ViewEventHandler) view).simulateOpenActive(activeContext, players)
                    ),
                "Error occurred while opening an active view '%s'!",
                view.instance()
            );
    }

    @NotNull
    @Override
    public PipelinesFrame pipelines() {
        return this.pipelines;
    }

    @NotNull
    @Override
    public Frame installFeature(@NotNull final Class<? extends Feature> feature) {
        // TODO: portlek, Implement this.
        return this;
    }

    @NotNull
    @Override
    public Frame installFeature(@NotNull final Feature feature) {
        // TODO: portlek, Implement this.
        return this;
    }
}
