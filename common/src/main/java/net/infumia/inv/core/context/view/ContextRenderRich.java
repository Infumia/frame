package net.infumia.inv.core.context.view;

import java.io.Closeable;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.infumia.inv.api.context.view.ContextRender;
import net.infumia.inv.api.element.Element;
import net.infumia.inv.api.service.ConsumerService;
import net.infumia.inv.api.viewer.Viewer;
import net.infumia.inv.core.config.ViewConfigRich;
import net.infumia.inv.core.context.ContextBaseRich;
import net.infumia.inv.core.slot.SlotFinder;
import net.infumia.inv.core.view.ViewContainerRich;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ContextRenderRich extends ContextBaseRich, ContextRender {
    @Override
    @NotNull
    ViewContainerRich container();

    @Override
    @NotNull
    ViewConfigRich config();

    @NotNull
    SlotFinder slotFinder();

    void updateTask(@Nullable Closeable task);

    @Nullable
    Closeable updateTask();

    void addElement(@NotNull Element element);

    @NotNull
    CompletableFuture<ConsumerService.State> simulateFirstRender();

    @NotNull
    CompletableFuture<ConsumerService.State> simulateNavigate(@NotNull Collection<Viewer> viewers);

    @NotNull
    CompletableFuture<ConsumerService.State> simulateResume(
        @NotNull ContextRender from,
        @NotNull Collection<Viewer> viewers
    );
}
