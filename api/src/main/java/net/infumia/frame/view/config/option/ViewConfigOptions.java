package net.infumia.frame.view.config.option;

import static net.infumia.frame.view.config.option.ViewConfigOption.create;

public final class ViewConfigOptions {

    public static final ViewConfigOption<Boolean> CANCEL_ON_CLICK = create("cancel-on-click", true);
    public static final ViewConfigOption<Boolean> CANCEL_ON_PICKUP = create(
        "cancel-on-pickup",
        true
    );
    public static final ViewConfigOption<Boolean> CANCEL_ON_DROP = create("cancel-on-drop", true);
    public static final ViewConfigOption<Boolean> CANCEL_ON_DRAG = create("cancel-on-drag", true);

    private ViewConfigOptions() {
        throw new IllegalAccessError("Utility class!");
    }
}
