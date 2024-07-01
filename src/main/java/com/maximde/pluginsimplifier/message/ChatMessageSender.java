package com.maximde.pluginsimplifier.message;

import lombok.NonNull;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

public class ChatMessageSender {

    public static void sendChatMessage(@NonNull CommandSender sender, @NonNull String message) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        Component sendMessage = miniMessage.deserialize(message);
        sender.sendMessage(sendMessage);
    }
}
