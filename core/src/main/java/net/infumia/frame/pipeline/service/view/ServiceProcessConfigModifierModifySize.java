package net.infumia.frame.pipeline.service.view;

import net.infumia.frame.Preconditions;
import net.infumia.frame.context.view.ContextOpen;
import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.view.config.ViewConfigBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ServiceProcessConfigModifierModifySize
    implements PipelineServiceConsumer<PipelineContextView.ProcessConfigModifier> {

    public static final PipelineServiceConsumer<
        PipelineContextView.ProcessConfigModifier
    > INSTANCE = new ServiceProcessConfigModifierModifySize();

    public static final String KEY = "modify-size";

    @NotNull
    @Override
    public String key() {
        return ServiceProcessConfigModifierModifySize.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.ProcessConfigModifier ctx) {
        final ContextOpen context = ctx.context();
        final ViewConfigBuilder builder = context.modifyConfig();
        final String@Nullable[] layout = builder.layout();
        if (layout == null) {
            return;
        }
        final int layoutLength = layout.length;
        if (layoutLength == 0) {
            return;
        }
        final int size = builder.size();
        Preconditions.state(
            size <= 0 || size == layoutLength,
            "The layout length '%s' differs from the set inventory size '%s'!",
            layoutLength,
            size
        );
        context
            .frame()
            .logger()
            .debug(
                "View's '%s' size modified according to the layout length '%s'.",
                context.view().instance(),
                layoutLength
            );
        builder.size(layoutLength);
    }

    private ServiceProcessConfigModifierModifySize() {}
}
