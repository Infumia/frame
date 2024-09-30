package net.infumia.frame.context.view;

import net.infumia.frame.config.ViewConfigBuilderRich;
import net.infumia.frame.context.ContextRich;
import org.jetbrains.annotations.NotNull;

public interface ContextInitRich extends ContextRich, ContextInit {
    @NotNull
    @Override
    ViewConfigBuilderRich configBuilder();
}
