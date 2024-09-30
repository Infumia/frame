package net.infumia.frame.context.view;

import java.io.Closeable;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.infumia.frame.config.ViewConfigRich;
import net.infumia.frame.context.ContextBaseRich;
import net.infumia.frame.element.Element;
import net.infumia.frame.service.ConsumerService;
import net.infumia.frame.slot.SlotFinder;
import net.infumia.frame.view.ViewContainerRich;
import net.infumia.frame.viewer.Viewer;
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
