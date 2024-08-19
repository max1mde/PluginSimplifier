# PluginSimplifier

An Advanced Library for making minecraft commands

## How to use it in your own project?

**Gradle installation**

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.max1mde:PluginSimplifier:1.1.0'
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
    <version>1.1.0</version>
</dependency>
```

# PluginHolder (required)
```java
package com.example.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public class MyPlugin extends JavaPlugin {
    
    @Override
    public void onEnable() {
        PluginHolder.setPluginInstance(this);
    }
}
```
Now, you can get the plugin instance using "PluginHolder.getPluginInstance()"
# Command Registration

To register a command, you can use the CommandRegistrar class

```java
CommandRegistrar.command("example", new ExampleCommandExecutor())
    .namespace("customnamespace")
    .usage("/example <args>")
    .description("This is an example command")
    .aliases(Arrays.asList("ex", "sample"))
    .permission("example.use")
    .completer(new ExampleTabCompleter())
    .register();
```

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
CommandRegistrar.command("heal", new HealCommand())
    .namespace("myplugin")
    .usage("/heal [player]")
    .description("Heals the specified player or yourself")
    .aliases(List.of("h"))
    .permission("myplugin.command.heal")
    .completer(new HealTabCompleter())
    .register();
```

**Don't forget to shade the library!**
