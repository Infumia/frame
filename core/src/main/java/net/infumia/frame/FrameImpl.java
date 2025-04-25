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
import net.infumia.frame.pipeline.executor.PipelineExecutorFrame;
import net.infumia.frame.pipeline.executor.PipelineExecutorFrameImpl;
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
    private final PipelineExecutorFrame pipelines = new PipelineExecutorFrameImpl(this);
    private final ViewFactory viewFactory = new ViewFactoryImpl();
    private final Logger logger;
    private final TaskFactory taskFactory;
    private final InventoryListener listener;
    private final MetadataAccessFactory metadataAccessFactory;
    private final ViewerFactory viewerFactory;
    private TypedKeyStorageFactory storageFactory = TypedKeyStorageFactory.create();
    private InventoryFactory inventoryFactory = InventoryFactoryBukkit.bukkitOrPaper();

    FrameImpl(
        @NotNull final Plugin plugin,
        @NotNull final Logger logger,
        final boolean unregisterOnDisable
    ) {
        this.logger = logger;
        this.taskFactory = new TaskFactoryImpl(plugin, logger);
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

    @Override
    public void register() {
        this.register(__ -> {});
    }

    @Override
    public void register(
        @NotNull final Consumer<TypedKeyStorageImmutableBuilder> instanceConfigurer
    ) {
        Preconditions.state(
            !this.registered.get(),
            "This frame is already registered! #register() method cannot be called twice!"
        );
        this.registered.set(true);
        this.executeViewCreation(this.unregisteredViews, instanceConfigurer)
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
                return this.pipelines.executeListenersRegistered();
            })
            .exceptionally(throwable -> {
                if (throwable instanceof CompletionException) {
                    throwable = throwable.getCause();
                }
                if (throwable != null) {
                    this.logger.error(throwable, "Error occurred while registering views!");
                    this.unregisterInternally();
                }
                return null;
            });
    }

    @Override
    public void unregister() {
        this.unregisterInternally();
    }

    @NotNull
    @Override
    public Frame with(@NotNull final Class<?> viewClass) {
        Preconditions.argument(!this.registered.get(), "This frame is registered!");
        this.intoUnregisteredViews(viewClass);
        return this;
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
        final View view = this.findView(viewClass);
        if (!(view instanceof ViewEventHandler)) {
            return CompletableFuture.completedFuture(null);
        }
        final TypedKeyStorageImmutableBuilder builder =
            this.storageFactory.createImmutableBuilder(new HashMap<>());
        initialDataConfigurer.accept(builder);
        return this.loggedFuture(
                ((ViewEventHandler) view).simulateOpen(players, builder.build()),
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
                ((ViewEventHandler) view).simulateOpenActive(activeContext, players),
                "Error occurred while opening an active view '%s'!",
                view.instance()
            );
    }

    @NotNull
    @Override
    public PipelineExecutorFrame pipelines() {
        return this.pipelines;
    }

    @NotNull
    private View findView(@NotNull final Class<?> viewClass) {
        Preconditions.state(
            this.registered.get(),
            "Before you open a view you must register this frame!"
        );
        return Preconditions.argumentNotNull(
            this.registeredViews.get(viewClass),
            "View '%s' is not registered!",
            viewClass
        );
    }

    private void intoUnregisteredViews(@NotNull final Class<?> viewClass) {
        Preconditions.argument(
            !this.unregisteredViews.contains(viewClass),
            "View class '%s' already registered.",
            viewClass
        );
        this.logger.debug("View class '%s' recognized.", viewClass);
        this.unregisteredViews.add(viewClass);
    }

    @NotNull
    private CompletableFuture<Collection<View>> executeViewCreation(
        @NotNull final Collection<Class<?>> views,
        @NotNull final Consumer<TypedKeyStorageImmutableBuilder> instanceConfigurer
    ) {
        return this.pipelines.executeViewCreated(views).thenCompose(instances ->
                this.pipelines.executeViewRegistered(instances, instanceConfigurer)
            );
    }

    private void unregisterInternally() {
        this.registered.set(false);
        this.metadataAccessFactory.clearCache(Bukkit.getOnlinePlayers());
        HandlerList.unregisterAll(this.listener);
        final HashMap<Class<?>, View> views = new HashMap<>(this.registeredViews);
        this.registeredViews.clear();
        this.executeViewUnRegistration(views);
    }

    private void executeViewUnRegistration(@NotNull final Map<Class<?>, View> views) {
        this.pipelines.executeViewUnregistered(views.values()).whenComplete((state, throwable) -> {
                if (throwable != null) {
                    this.logger.error(
                            throwable,
                            "Error occurred while unregistering views '%s'!",
                            views.keySet()
                        );
                }
            });
    }

    @NotNull
    @Override
    public Frame installFeature(final Class<? extends Feature> feature) {
        // TODO: portlek, Implement this.
        return this;
    }

    @NotNull
    @Override
    public Frame installFeature(final Feature feature) {
        // TODO: portlek, Implement this.
        return this;
    }
}
