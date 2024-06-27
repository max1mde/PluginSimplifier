package com.maximde.pluginsimplifier.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ChatMessageSender {

    public static void sendChatMessage(@NotNull CommandSender sender, @NotNull String message) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        Component sendMessage = miniMessage.deserialize(message);
        sender.sendMessage(sendMessage);
    }
}
