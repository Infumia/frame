# frame
[![Maven Central Version](https://img.shields.io/maven-central/v/net.infumia/frame)](https://central.sonatype.com/artifact/net.infumia/frame)
## How to Use (Developers)
### Gradle
```groovy
repositories {
    mavenCentral()
    maven("https://central.sonatype.com/repository/maven-snapshots/")
}

dependencies {
    // Base modules
    implementation "net.infumia:frame:VERSION"

    // Annotation modules
    implementation "net.infumia:frame-annotations:VERSION"
    
    // Guice (optional)
    implementation "com.google.inject:guice:7.0.0"
}
```
### Code
```java
public final class Plugin extends JavaPlugin {
    public static final TypedKey<CommandSender> CONSOLE_KEY =
        TypedKey.of(CommandSender.class, "console");

    private final Frame frame = Frame.create(this)
        .with(ViewExample.class)
        .with(ViewAnnotationExample.class)
        .install(AnnotationFeature.class);
 
    @Override
    public void onEnable() {
        this.frame.register(builder -> 
            builder.add(Plugin.CONSOLE_KEY, Bukkit.getConsoleSender()));
    }
}
```
```java
public final class ViewExample implements ViewHandler {

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
            .onClick(context -> {
                context.closeForViewer();
                sender.sendMessage("Player " + context.clicker().player() + " clicked to a diamond!");
            });
    }
}
```
```java
@ViewCancelOnClick
@ViewType(InvType.CHEST)
@ViewLayout({
    "xxxxxxxxx",
    "xxxxaxxxx",
    "xxxxxxxxx"
})
public final class ViewAnnotationExample {

    @ViewTitle
    public String onTitle(final Player viewer) {
        return "Player: " + player.getName();
    }

    @ElementSlot(layout = 'x')
    @ElementItem(configKey = "fill-item")
    public void glasses() {}

    @ElementCloseOnClick
    @ElementSlot(layout = 'a')
    @ElementItemStack(configKey = "diamond-item")
    public void diamond(
        final Player viewer,
        /*
        Optional if there is only one CommandSender.class registered.
        @InstanceKey("console")*/
        final CommandSender sender
    ) {
        sender.sendMessage("Player " + viewer.getName() + " clicked to a diamond!");
    }
}
```
