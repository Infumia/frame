package net.infumia.frame.annotations.decorator.view;

import net.infumia.frame.annotations.ViewDecorator;
import net.infumia.frame.annotations.ViewTitle;
import net.infumia.frame.service.Implementation;
import net.infumia.frame.view.View;
import net.infumia.frame.view.config.ViewConfigBuilder;
import org.jetbrains.annotations.NotNull;

public final class ViewTitleDecorator implements ViewDecorator<ViewTitle> {

    @Override
    public void decorate(@NotNull final View view, @NotNull final ViewTitle annotation) {
        view
            .pipelines()
            .applyInit(
                Implementation.register(ctx -> {
                    final ViewConfigBuilder builder = ctx.configBuilder();
                    final String title = annotation.value();

                    if (!title.isEmpty()) {
                        builder.title(title);
                    }
                })
            );
    }
}
