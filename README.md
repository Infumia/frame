# frame
[![Maven Central Version](https://img.shields.io/maven-central/v/net.infumia/frame)](https://central.sonatype.com/artifact/net.infumia/frame)
## How to Use (Developers)
### Gradle
```groovy
repositories {
    mavenCentral()
}

dependencies {
    // Base modules
    implementation "net.infumia:frame:VERSION"
    runtimeOnly "net.infumia:frame-core:VERSION"
}
```
### Code
```java
public final class Plugin extends JavaPlugin {
    
    private final Frame frame = Frame.create(this)
        .with();
    
    @Override
    public void onEnable() {
        this.frame.register();
    }
}

public final class View implements ViewHandler {

    @Override
    public void onInit(@NotNull final ContextInit ctx) {
        ctx.configBuilder().type(InvType.CHEST).cancelOnClick();
    }

    @Override
    public void onOpen(@NotNull final ContextOpen ctx) {
        final Viewer viewer = ctx.viewer();
        ctx
            .modifyConfig()
            .layout(new String[] {
                "xxxxxxxxx",
                "xxxxaxxxx",
                "xxxxxxxxx"
            })
            .title("Player: " + viewer.player().getName());
    }

    @Override
    public void onFirstRender(@NotNull final ContextRender ctx) {
        ctx.layoutSlot('x', new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

        ctx
            .layoutSlot('a', new ItemStack(Material.DIAMOND))
            .cancelOnClick()
            .onClick(context -> {
                context.closeForViewer();
                context.clicker().sendMessage("You've clicked to a diamond!");
            });
    }
}
```
