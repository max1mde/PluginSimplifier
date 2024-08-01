# PluginSimplifier

An Advanced Library for making minecraft commands

## How to use it in your own project?

**Gradle installation**

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.max1mde:PluginSimplifier:1.0.3'
}
```

**Maven installation**

```xml

<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.max1mde</groupId>
    <artifactId>PluginSimplifier</artifactId>
    <version>1.0.0</version>
</dependency>
```

# Concepts

Command Registration
To register a command, you can use the CommandRegistrar class, which provides several overloaded methods for different
levels of command customization. Here’s a breakdown of the registration methods:

Methods

```java
CommandRegistrar.registerCommand(String name, CommandExecutor executor);
```

Registers a command with the specified name and executor.

```java
CommandRegistrar.registerCommand(String name, CommandExecutor executor, String namespace);
```

Registers a command with a specified namespace.

```java
CommandRegistrar.registerCommand(String name, CommandExecutor executor, String namespace, String usage);
```

Registers a command with a specified usage format.

```java
CommandRegistrar.registerCommand(String name, CommandExecutor executor, String namespace, String usage, String description);
```

Registers a command with a description.

```java
CommandRegistrar.registerCommand(String name, CommandExecutor executor, String namespace, String usage, String description, List<String>
        aliases);
```

Registers a command with aliases.

```java
CommandRegistrar.registerCommand(String name, CommandExecutor executor, String namespace, String usage, String description, List<String>
        aliases, String permission);
```

Registers a command with a specified permission.

```java
registerCommand(String name, CommandExecutor executor, String namespace, String usage, String description, List<String>
        aliases, String permission, TabCompleter completer);
```

Registers a command with all available options, including a tab completer.

Example Usage
Here’s a full example of how to implement and register a custom command using the command system.

Example Command: /heal [player]
The following example demonstrates a /heal command that heals a player or themselves:

```java
package com.example.plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HealCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 0) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage("Player not found");
                return true;
            }
            target.setHealth(20);
            target.sendMessage("You have been healed!");
            sender.sendMessage("Healed " + target.getName());
        } else {
            if (sender instanceof Player player) {
                player.setHealth(20);
                player.sendMessage("You have been healed!");
                return true;
            } else {
                sender.sendMessage("You must specify a player or run this command as a player");
            }
        }
        return true;
    }
}
```

Registering the Command

To register the HealCommand, you would use the CommandRegistrar like this:

```java
CommandRegistrar.registerCommand(
    "heal",
            new HealCommand(),
    "myplugin",
            "/heal [player]",
            "Heals the specified player or yourself",
            List.

of("h"),
    "myplugin.command.heal",
            new

HealTabCompleter()
);
```

Command Parameters

name: The name of the command (e.g., "heal").

executor: The class that implements CommandExecutor (e.g., new HealCommand).

namespace: The plugin's namespace for the command (optional).

usage: A string describing how to use the command (optional).

description: A brief description of what the command does (optional).

aliases: A list of alternative names for the command (optional).

permission: A string that specifies the required permission to execute the command (optional).

completer: A TabCompleter for command tab completion (optional).

Don't forget to shade the library
