package net.infumia.inv.api.view.config.option;

import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface ViewConfigOptionController {
    @NotNull
    <T> Optional<T> option(@NotNull ViewConfigOption<T> option);

    @NotNull
    <T> Optional<T> optionOrDefault(@NotNull ViewConfigOption<T> option);
}
