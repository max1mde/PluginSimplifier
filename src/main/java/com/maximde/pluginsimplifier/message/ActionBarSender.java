package com.maximde.pluginsimplifier.message;

import lombok.NonNull;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

public class ActionBarSender {

    public static void sendActionBar(@NonNull Player player, @NonNull String message) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        Component sendMessage = miniMessage.deserialize(message);
        player.sendActionBar(sendMessage);
    }
}