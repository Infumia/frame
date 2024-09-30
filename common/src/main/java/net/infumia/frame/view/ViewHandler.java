package net.infumia.frame.view;

import net.infumia.frame.context.view.ContextClick;
import net.infumia.frame.context.view.ContextClose;
import net.infumia.frame.context.view.ContextInit;
import net.infumia.frame.context.view.ContextOpen;
import net.infumia.frame.context.view.ContextRender;
import net.infumia.frame.context.view.ContextResume;
import net.infumia.frame.viewer.ContextualViewer;
import org.jetbrains.annotations.NotNull;

public interface ViewHandler {
    default void onInit(@NotNull final ContextInit ctx) {}

    default void onOpen(@NotNull final ContextOpen ctx) {}

    default void onFirstRender(@NotNull final ContextRender ctx) {}

    default void onViewerAdded(@NotNull final ContextualViewer viewer) {}

    default void onViewerRemoved(@NotNull final ContextualViewer viewer) {}

    default void onResume(@NotNull final ContextResume context) {}

    default void onUpdate(@NotNull final ContextRender ctx) {}

    default void onClick(@NotNull final ContextClick ctx) {}

    default void onClose(@NotNull final ContextClose ctx) {}
}
