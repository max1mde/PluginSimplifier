package com.maximde.pluginsimplifier.message;

import lombok.NonNull;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

import java.time.Duration;

public class TitleSender {

    public static void sendTitle(@NonNull Player player, @NonNull String titleMessage, @NonNull String subtitleMessage, int fadeIn, int stay, int fadeOut) {
        MiniMessage miniMessage = MiniMessage.miniMessage();

        Component titleComponent = miniMessage.deserialize(titleMessage);
        Component subtitleComponent = miniMessage.deserialize(subtitleMessage);

        Title.Times times = Title.Times.times(
                Duration.ofSeconds(fadeIn), // fadeIn
                Duration.ofSeconds(stay), // stay
                Duration.ofSeconds(fadeOut)  // fadeOut
        );

        Title title = Title.title(
                titleComponent,
                subtitleComponent,
                times
        );

        player.showTitle(title);
    }

    public static class Builder {
        private Player player;
        private String titleMessage;
        private String subtitleMessage;
        private int fadeIn;
        private int stay;
        private int fadeOut;

        public Builder player(@NonNull Player player) {
            this.player = player;
            return this;
        }

        public Builder titleMessage(@NonNull String titleMessage) {
            this.titleMessage = titleMessage;
            return this;
        }

        public Builder subtitleMessage(@NonNull String subtitleMessage) {
            this.subtitleMessage = subtitleMessage;
            return this;
        }

        public Builder fadeIn(int fadeIn) {
            this.fadeIn = fadeIn;
            return this;
        }

        public Builder stay(int stay) {
            this.stay = stay;
            return this;
        }

        public Builder fadeOut(int fadeOut) {
            this.fadeOut = fadeOut;
            return this;
        }

        public void send() {
            TitleSender.sendTitle(player, titleMessage, subtitleMessage, fadeIn, stay, fadeOut);
        }
    }
}