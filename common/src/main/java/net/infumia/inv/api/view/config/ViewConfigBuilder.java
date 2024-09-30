package net.infumia.inv.api.view.config;

import java.time.Duration;
import net.infumia.inv.api.type.InvType;
import net.infumia.inv.api.view.config.option.ViewConfigOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ViewConfigBuilder extends ViewConfig {
    @NotNull
    ViewConfigBuilder title(@NotNull Object title);

    @NotNull
    ViewConfigBuilder layout(@NotNull String@Nullable[] layout);

    @NotNull
    ViewConfigBuilder size(int size);

    @NotNull
    ViewConfigBuilder type(@NotNull InvType type);

    @NotNull
    ViewConfigBuilder updateInterval(@NotNull Duration updateInterval);

    @NotNull
    ViewConfigBuilder interactionDelay(@NotNull Duration interactionDelay);

    @NotNull
    ViewConfigBuilder cancelOnClick();

    @NotNull
    ViewConfigBuilder cancelOnPickup();

    @NotNull
    ViewConfigBuilder cancelOnDrop();

    @NotNull
    ViewConfigBuilder cancelOnDrag();

    @NotNull
    ViewConfigBuilder cancelDefaults();

    @NotNull
    <T> ViewConfigBuilder addOption(@NotNull ViewConfigOption<T> option);

    @NotNull
    <T> ViewConfigBuilder addOption(@NotNull ViewConfigOption<T> option, @NotNull T value);

    @NotNull
    ViewConfigBuilder addModifier(@NotNull ViewConfigModifier modifier);

    @NotNull
    ViewConfigBuilder addModifier(@NotNull ViewConfigModifier... modifiers);
}
