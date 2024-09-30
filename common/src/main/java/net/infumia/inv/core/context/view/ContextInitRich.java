package net.infumia.inv.core.context.view;

import net.infumia.inv.api.context.view.ContextInit;
import net.infumia.inv.core.config.ViewConfigBuilderRich;
import net.infumia.inv.core.context.ContextRich;
import org.jetbrains.annotations.NotNull;

public interface ContextInitRich extends ContextRich, ContextInit {
    @NotNull
    @Override
    ViewConfigBuilderRich configBuilder();
}
