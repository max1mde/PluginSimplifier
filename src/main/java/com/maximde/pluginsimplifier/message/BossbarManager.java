package com.maximde.pluginsimplifier.message;

import lombok.NonNull;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.bossbar.BossBar.Color;
import net.kyori.adventure.bossbar.BossBar.Flag;
import net.kyori.adventure.bossbar.BossBar.Overlay;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

import java.util.*;

public class BossbarManager {
    private static final Map<UUID, BossBar> playerBossBars = new HashMap<>();
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public static void createBossBar(@NonNull Player player, @NonNull String title, @NonNull Color color, @NonNull Overlay overlay, float progress, @NonNull Set<Flag> flags) {
        Component titleComponent = miniMessage.deserialize(title);
        BossBar bossBar = BossBar.bossBar(titleComponent, progress, color, overlay, flags);
        playerBossBars.put(player.getUniqueId(), bossBar);
        player.showBossBar(bossBar);
    }

    public static void removeBossBar(@NonNull Player player) {
        UUID playerUUID = player.getUniqueId();
        BossBar bossBar = playerBossBars.remove(playerUUID);
        player.hideBossBar(bossBar);
    }

    public static void updateBossBarTitle(@NonNull Player player, @NonNull String newTitle) {
        BossBar bossBar = playerBossBars.get(player.getUniqueId());
        if (bossBar != null) {
            Component titleComponent = miniMessage.deserialize(newTitle);
            bossBar.name(titleComponent);
        }
    }

    public static void updateBossBarProgress(@NonNull Player player, float newProgress) {
        BossBar bossBar = playerBossBars.get(player.getUniqueId());
        if (bossBar != null) {
            bossBar.progress(newProgress);
        }
    }

    public static void updateBossBarColor(@NonNull Player player, @NonNull Color newColor) {
        BossBar bossBar = playerBossBars.get(player.getUniqueId());
        if (bossBar != null) {
            bossBar.color(newColor);
        }
    }

    public static void updateBossBarOverlay(@NonNull Player player, @NonNull Overlay newOverlay) {
        BossBar bossBar = playerBossBars.get(player.getUniqueId());
        if (bossBar != null) {
            bossBar.overlay(newOverlay);
        }
    }

    public static void addBossBarFlag(@NonNull Player player, @NonNull Flag flag) {
        BossBar bossBar = playerBossBars.get(player.getUniqueId());
        if (bossBar != null) {
            bossBar.addFlag(flag);
        }
    }

    public static void removeBossBarFlag(@NonNull Player player, @NonNull Flag flag) {
        BossBar bossBar = playerBossBars.get(player.getUniqueId());
        if (bossBar != null) {
            bossBar.removeFlag(flag);
        }
    }

    public static class Builder {
        private Player player;
        private String title;
        private Color color = Color.WHITE;
        private Overlay overlay = Overlay.PROGRESS;
        private float progress = 1.0f;
        private Set<Flag> flags = new HashSet<>();

        public Builder player(@NonNull Player player) {
            this.player = player;
            return this;
        }

        public Builder title(@NonNull String title) {
            this.title = title;
            return this;
        }

        public Builder color(@NonNull Color color) {
            this.color = color;
            return this;
        }

        public Builder overlay(@NonNull Overlay overlay) {
            this.overlay = overlay;
            return this;
        }

        public Builder progress(float progress) {
            this.progress = progress;
            return this;
        }

        public Builder flag(@NonNull Flag flag) {
            this.flags.add(flag);
            return this;
        }

        public void create() {
            BossbarManager.createBossBar(player, title, color, overlay, progress, flags);
        }
    }
}