package net.infumia.inv.core;

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
import net.infumia.inv.api.InventoryManager;
import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.logger.Logger;
import net.infumia.inv.api.metadata.MetadataAccessFactory;
import net.infumia.inv.api.pipeline.executor.PipelineExecutorManager;
import net.infumia.inv.api.task.TaskFactory;
import net.infumia.inv.api.typedkey.TypedKeyStorageFactory;
import net.infumia.inv.api.typedkey.TypedKeyStorageImmutableBuilder;
import net.infumia.inv.api.util.Preconditions;
import net.infumia.inv.api.view.View;
import net.infumia.inv.api.view.ViewCreator;
import net.infumia.inv.api.view.creator.InventoryCreator;
import net.infumia.inv.api.viewer.ViewerCreator;
import net.infumia.inv.core.context.view.ContextRenderRich;
import net.infumia.inv.core.extension.CompletableFutureExtensions;
import net.infumia.inv.core.listener.InventoryListener;
import net.infumia.inv.core.metadata.MetadataAccessFactoryImpl;
import net.infumia.inv.core.pipeline.executor.PipelineExecutorManagerImpl;
import net.infumia.inv.core.task.TaskFactoryImpl;
import net.infumia.inv.core.view.ViewCreatorImpl;
import net.infumia.inv.core.view.ViewEventHandler;
import net.infumia.inv.core.view.creator.InventoryCreatorBukkit;
import net.infumia.inv.core.viewer.ViewerCreatorImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class InventoryManagerImpl implements InventoryManagerRich {

    private final Collection<Class<?>> unregisteredViews = ConcurrentHashMap.newKeySet();
    private final Map<Class<?>, View> registeredViews = new ConcurrentHashMap<>();
    private final AtomicBoolean registered = new AtomicBoolean(false);
    private final PipelineExecutorManager pipelines = new PipelineExecutorManagerImpl(this);
    private final ViewCreator viewCreator = new ViewCreatorImpl();
    private final Logger logger;
    private final TaskFactory taskFactory;
    private final InventoryListener listener;
    private final MetadataAccessFactory metadataAccessFactory;
    private final ViewerCreator viewerCreator;
    private TypedKeyStorageFactory storageFactory = TypedKeyStorageFactory.create();
    private InventoryCreator inventoryCreator = InventoryCreatorBukkit.INSTANCE;

    InventoryManagerImpl(
        @NotNull final Plugin plugin,
        @NotNull final Logger logger,
        final boolean unregisterOnDisable
    ) {
        this.logger = logger;
        this.taskFactory = new TaskFactoryImpl(plugin, logger);

        this.metadataAccessFactory = new MetadataAccessFactoryImpl(plugin);
        this.listener = new InventoryListener(plugin, logger, this.metadataAccessFactory, () -> {
            if (unregisterOnDisable) {
                this.unregister();
            }
        });
        this.viewerCreator = new ViewerCreatorImpl(this.metadataAccessFactory);
    }

    @NotNull
    @Override
    public InventoryListener listener() {
        return this.listener;
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
    public ViewCreator viewCreator() {
        return this.viewCreator;
    }

    @NotNull
    @Override
    public ViewerCreator viewerCreator() {
        return this.viewerCreator;
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
    public InventoryCreator inventoryCreator() {
        return this.inventoryCreator;
    }

    @Override
    public void inventoryCreator(@NotNull final InventoryCreator inventoryCreator) {
        this.inventoryCreator = inventoryCreator;
    }

    @Override
    public void register() {
        Preconditions.state(
            !this.registered.get(),
            "This inventory manager is already registered! #register() method cannot be called twice!"
        );
        this.registered.set(true);
        this.executeViewCreation(this.unregisteredViews)
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
    public InventoryManager with(@NotNull final Class<?> viewClass) {
        Preconditions.argument(!this.registered.get(), "This inventory manager is registered!");
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
            this.storageFactory.createImmutableBuilder();
        initialDataConfigurer.accept(builder);
        return CompletableFutureExtensions.logError(
            ((ViewEventHandler) view).simulateOpen(players, builder.build()),
            this.logger,
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
        if (!(view instanceof ViewEventHandler)) {
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFutureExtensions.logError(
            ((ViewEventHandler) view).simulateOpenActive(
                    (ContextRenderRich) activeContext,
                    players
                ),
            this.logger,
            "Error occurred while opening an active view '%s'!",
            view.instance()
        );
    }

    @NotNull
    @Override
    public PipelineExecutorManager pipelines() {
        return this.pipelines;
    }

    @NotNull
    private View findView(@NotNull final Class<?> viewClass) {
        Preconditions.state(
            this.registered.get(),
            "Before you open a view you must register this inventory manager!"
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
        @NotNull final Collection<Class<?>> views
    ) {
        return this.pipelines.executeViewCreated(views).thenCompose(
                this.pipelines::executeViewRegistered
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
}
