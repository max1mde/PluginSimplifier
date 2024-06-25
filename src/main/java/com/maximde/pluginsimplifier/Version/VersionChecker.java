package com.maximde.pluginsimplifier.Version;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 * A utility class for checking the server version against a target version.
 * Provides methods to compare if the server version is higher, lower, or the same as a target version.
 */
public class VersionChecker {
    private static final Comparator<String> VERSION_COMPARATOR = (v1, v2) -> {
        String[] parts1 = v1.split("\\.");
        String[] parts2 = v2.split("\\.");

        int length = Math.max(parts1.length, parts2.length);
        for (int i = 0; i < length; i++) {
            int part1 = i < parts1.length ? Integer.parseInt(parts1[i]) : 0;
            int part2 = i < parts2.length ? Integer.parseInt(parts2[i]) : 0;
            if (part1 != part2) {
                return Integer.compare(part1, part2);
            }
        }
        return 0;
    };

    /**
     * Retrieves the server version.
     *
     * @return the server version in the format major.minor.patch (e.g., 1.16.5)
     */
    private static String getServerVersion() {
        String version = Bukkit.getBukkitVersion();
        return version.split("-")[0];
    }

    /**
     * Checks if the server version is higher than the target version.
     *
     * @param targetVersion the version to compare against
     * @return true if the server version is higher than the target version, false otherwise
     */
    public static boolean isHigher(@NotNull String targetVersion) {
        String serverVersion = getServerVersion();
        return VERSION_COMPARATOR.compare(serverVersion, targetVersion) > 0;
    }

    /**
     * Checks if the server version is lower than the target version.
     *
     * @param targetVersion the version to compare against
     * @return true if the server version is lower than the target version, false otherwise
     */
    public static boolean isLower(@NotNull String targetVersion) {
        String serverVersion = getServerVersion();
        return VERSION_COMPARATOR.compare(serverVersion, targetVersion) < 0;
    }

    /**
     * Checks if the server version is the same as the target version.
     *
     * @param targetVersion the version to compare against
     * @return true if the server version is the same as the target version, false otherwise
     */
    public static boolean isSame(@NotNull String targetVersion) {
        String serverVersion = getServerVersion();
        return VERSION_COMPARATOR.compare(serverVersion, targetVersion) == 0;
    }
}