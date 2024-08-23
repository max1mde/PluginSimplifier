package com.maximde.pluginsimplifier.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

/**
 * Provides context information for a command execution.
 */
@SuppressWarnings("unused")
public record CommandContext(CommandSender sender, String label, String[] args) {
    /**
     * Creates a new CommandContext with the provided sender, label, and arguments.
     *
     * @param sender The command sender.
     * @param label  The command label.
     * @param args   The command arguments.
     */
    public CommandContext {
    }

    /**
     * Gets the command sender.
     *
     * @return The command sender.
     */
    @Override
    public CommandSender sender() {
        return sender;
    }

    /**
     * Gets the command label.
     *
     * @return The command label.
     */
    @Override
    public String label() {
        return label;
    }

    /**
     * Gets the command arguments.
     *
     * @return An array of the command arguments.
     */
    @Override
    public String[] args() {
        return args;
    }

    /**
     * Gets the player, Not recommended to use except you have checked that it's a player
     *
     * @return The player
     */
    public Player player() {
        return (Player) sender;
    }

    /**
     * Checks if the sender is a player.
     *
     * @return True if the sender is a player, false otherwise.
     */
    public boolean isPlayer() {
        return sender instanceof Player;
    }

    /**
     * Checks if the sender is the console.
     *
     * @return True if the sender is the console, false otherwise.
     */
    public boolean isConsole() {
        return sender instanceof ConsoleCommandSender;
    }

    /**
     * Checks if the sender has the specified permission.
     *
     * @param permission The permission to check.
     * @return True if the sender has the permission, false otherwise.
     */
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

    /**
     * Gets the argument at the specified index.
     *
     * @param index The index of the argument.
     * @return The argument at the specified index.
     * @throws CommandExecutionException if the index is out of range.
     */
    public String arg(int index) {
        if (index < 0 || index >= args.length) {
            throw new CommandExecutionException("Invalid argument index: " + index);
        }
        return args[index];
    }

    /**
     * Gets the arguments as a list.
     *
     * @return A list of the command arguments.
     */
    public List<String> argList() {
        return Arrays.asList(args);
    }

    /**
     * Checks if the specified argument index exists.
     *
     * @param index The index to check.
     * @return True if the index exists, false otherwise.
     */
    public boolean hasArg(int index) {
        return index >= 0 && index < args.length;
    }

    /**
     * Checks if the specified flag is present.
     *
     * @param flag The flag to check (without the leading hyphen).
     * @return True if the flag is present, false otherwise.
     */
    public boolean hasFlag(String flag) {
        return Arrays.stream(args).anyMatch(arg -> arg.equalsIgnoreCase(flag));
    }

    /**
     * Checks if the sender is a player and throws a CommandExecutionException if not.
     *
     * @param message The exception message to throw.
     * @throws CommandExecutionException if the sender is not a player.
     */
    public void checkConsole(String message) {
        if (!isPlayer()) {
            throw new CommandExecutionException(message);
        }
    }

    /**
     * Checks if the sender is not console and throws a CommandExecutionException if not.
     *
     * @param message The exception message to throw.
     * @throws CommandExecutionException if the sender is a player.
     */
    public void checkPlayer(String message) {
        if (!isConsole()) {
            throw new CommandExecutionException(message);
        }
    }
}