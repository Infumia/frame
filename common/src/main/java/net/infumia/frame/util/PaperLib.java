package net.infumia.frame.util;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public final class PaperLib {

    public static boolean isVersion(final int minor) {
        return PaperLib.isVersion(minor, 0);
    }

    public static boolean isVersion(final int minor, final int patch) {
        return PaperLib.minor() > minor || (PaperLib.minor() >= minor && PaperLib.patch() >= patch);
    }

    public static int major() {
        return PaperLib.environment.major;
    }

    public static int minor() {
        return PaperLib.environment.minor;
    }

    public static int patch() {
        return PaperLib.environment.patch;
    }

    public static boolean isPaper() {
        return PaperLib.environment.isPaper;
    }

    private static final Pattern VERSION_PATTERN = Pattern.compile(
        "(?i)\\(MC: (\\d)\\.(\\d+)\\.?(\\d+?)?(?: (Pre-Release|Release Candidate) )?(\\d)?\\)"
    );

    private static final Environment environment = PaperLib.initialize();

    @NotNull
    private static Environment initialize() {
        final Matcher matcher = PaperLib.VERSION_PATTERN.matcher(Bukkit.getVersion());
        int major = 1;
        int minor = 0;
        int patch = 0;
        if (matcher.find()) {
            final MatchResult matchResult = matcher.toMatchResult();
            try {
                major = Integer.parseInt(matchResult.group(1), 10);
            } catch (final Exception ignored) {}
            try {
                minor = Integer.parseInt(matchResult.group(2), 10);
            } catch (final Exception ignored) {}
            if (matchResult.groupCount() >= 3) {
                try {
                    patch = Integer.parseInt(matchResult.group(3), 10);
                } catch (final Exception ignored) {}
            }
        }
        if (
            PaperLib.hasClass("com.destroystokyo.paper.PaperConfig") ||
            PaperLib.hasClass("io.papermc.paper.configuration.Configuration")
        ) {
            return new Environment(major, minor, patch, true);
        }
        return new Environment(major, minor, patch, false);
    }

    private static boolean hasClass(@NotNull final String className) {
        try {
            Class.forName(className);
            return true;
        } catch (final ClassNotFoundException e) {
            return false;
        }
    }

    static final class Environment {

        private final int major;
        private final int minor;
        private final int patch;
        private final boolean isPaper;

        private Environment(
            final int major,
            final int minor,
            final int patch,
            final boolean isPaper
        ) {
            this.major = major;
            this.minor = minor;
            this.patch = patch;
            this.isPaper = isPaper;
        }
    }
}
