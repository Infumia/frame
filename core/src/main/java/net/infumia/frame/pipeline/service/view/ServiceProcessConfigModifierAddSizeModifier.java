package net.infumia.frame.pipeline.service.view;

import net.infumia.frame.pipeline.PipelineServiceConsumer;
import net.infumia.frame.pipeline.context.PipelineContextView;
import net.infumia.frame.util.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ServiceProcessConfigModifierAddSizeModifier
    implements PipelineServiceConsumer<PipelineContextView.ProcessConfigModifier> {

    public static final PipelineServiceConsumer<
        PipelineContextView.ProcessConfigModifier
    > INSTANCE = new ServiceProcessConfigModifierAddSizeModifier();

    public static final String KEY = "add-size-modifier";

    @Override
    public String key() {
        return ServiceProcessConfigModifierAddSizeModifier.KEY;
    }

    @Override
    public void accept(@NotNull final PipelineContextView.ProcessConfigModifier ctx) {
        ctx
            .context()
            .modifyConfig()
            .addModifier((builder, context) -> {
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
                    .manager()
                    .logger()
                    .debug(
                        "View's '%s' size modified according to the layout length '%s'.",
                        context.view().instance(),
                        layoutLength
                    );
                builder.size(layoutLength);
            });
    }

    private ServiceProcessConfigModifierAddSizeModifier() {}
}
