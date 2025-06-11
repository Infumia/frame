package net.infumia.frame.annotations.decorator.view;

import net.infumia.frame.annotations.ViewDecorator;
import net.infumia.frame.view.View;
import net.infumia.frame.view.config.ViewConfigBuilder;
import org.jetbrains.annotations.NotNull;

public final class ViewSizeDecorator implements ViewDecorator<ViewSize> {

    @Override
    public void decorate(@NotNull final View view, @NotNull final ViewSize annotation) {
        view
            .pipelines()
            .applyInit((context, state) -> {
                final ViewConfigBuilder builder = context.configBuilder();
                final int size = annotation.value();

                if (size != ViewSize.INFERRED_VALUE) {
                    builder.size(size);
                }
            });
    }
}
