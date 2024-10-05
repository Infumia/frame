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
    public static final TypedKey<CommandSender> CONSOLE_KEY =
        TypedKey.of(CommandSender.class, "console");
    
    private final Frame frame = Frame.create(this)
        .with(View.class);
    
    @Override
    public void onEnable() {
        /*this.frame.register();*/
        this.frame.register(builder -> 
            builder.add(Plugin.CONSOLE_KEY, Bukkit.getConsoleSender()));
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
        final CommandSender sender = ctx.instances().getOrThrow(Plugin.CONSOLE_KEY);
        ctx.layoutSlot('x', new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

        ctx
            .layoutSlot('a', new ItemStack(Material.DIAMOND))
            .cancelOnClick()
            .onClick(context -> {
                context.closeForViewer();
                sender.sendMessage("Player " + context.clicker().player() + " clicked to a diamond!");
            });
    }
}
```
