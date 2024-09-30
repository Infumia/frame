package net.infumia.inv.core.pipeline.context;

import java.util.Collection;
import net.infumia.inv.api.InventoryManager;
import net.infumia.inv.api.pipeline.context.PipelineContextManager;
import net.infumia.inv.api.view.View;
import net.infumia.inv.core.InventoryManagerRich;
import org.jetbrains.annotations.NotNull;

public interface PipelineContextManagers {
    final class ViewCreated implements PipelineContextManager.ViewCreated {

        private final InventoryManagerRich manager;
        private final Collection<Class<?>> registeredViews;

        public ViewCreated(
            @NotNull final InventoryManagerRich manager,
            @NotNull final Collection<Class<?>> registeredViews
        ) {
            this.manager = manager;
            this.registeredViews = registeredViews;
        }

        @NotNull
        @Override
        public Collection<Class<?>> registeredViews() {
            return this.registeredViews;
        }

        @NotNull
        @Override
        public InventoryManager manager() {
            return this.manager;
        }
    }

    final class ViewRegistered implements PipelineContextManager.ViewRegistered {

        private final InventoryManagerRich manager;
        private final Collection<Object> registeredViews;

        public ViewRegistered(
            @NotNull final InventoryManagerRich manager,
            @NotNull final Collection<Object> registeredViews
        ) {
            this.manager = manager;
            this.registeredViews = registeredViews;
        }

        @NotNull
        @Override
        public InventoryManagerRich manager() {
            return this.manager;
        }

        @NotNull
        @Override
        public Collection<Object> registeredViews() {
            return this.registeredViews;
        }
    }

    final class ListenerRegistered implements PipelineContextManager.ListenerRegistered {

        private final InventoryManager manager;

        public ListenerRegistered(@NotNull final InventoryManager manager) {
            this.manager = manager;
        }

        @NotNull
        @Override
        public InventoryManager manager() {
            return this.manager;
        }
    }

    final class ViewUnregistered implements PipelineContextManager.ViewUnregistered {

        private final InventoryManagerRich manager;
        private final Collection<View> unregisteredViews;

        public ViewUnregistered(
            @NotNull final InventoryManagerRich manager,
            @NotNull final Collection<View> unregisteredViews
        ) {
            this.manager = manager;
            this.unregisteredViews = unregisteredViews;
        }

        @NotNull
        @Override
        public InventoryManagerRich manager() {
            return this.manager;
        }

        @NotNull
        @Override
        public Collection<View> unregisteredViews() {
            return this.unregisteredViews;
        }
    }
}
