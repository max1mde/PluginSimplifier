package com.maximde.pluginsimplifier.Command;

import com.maximde.plugin.PluginHolder;
import com.maximde.plugin.PluginSimplifier;
import com.maximde.pluginsimplifier.Annotations.Completer;
import com.maximde.pluginsimplifier.Annotations.Register;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;

public class CommandRegistry {
    public static void registerCommands() {
        PluginSimplifier plugin = PluginHolder.getPluginInstance();
        URLClassLoader classLoader = (URLClassLoader) plugin.getClass().getClassLoader();

        try {
            URL jarUrl = classLoader.getURLs()[0];
            File jarFile = new File(jarUrl.toURI());

            try (JarFile jar = new JarFile(jarFile)) {
                Enumeration<JarEntry> entries = jar.entries();

                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().endsWith(".class")) {
                        String className = entry.getName().replace('/', '.').replace(".class", "");
                        Class<?> clazz = Class.forName(className);

                        boolean hasRegisterAnnotation = false;
                        boolean hasCompleterAnnotation = false;

                        for (Method method : clazz.getDeclaredMethods()) {
                            if (method.isAnnotationPresent(Register.class)) {
                                hasRegisterAnnotation = true;
                            }
                            if (method.isAnnotationPresent(Completer.class)) {
                                hasCompleterAnnotation = true;
                            }
                        }

                        if (hasRegisterAnnotation) {
                            if (CommandExecutor.class.isAssignableFrom(clazz)) {
                                CommandExecutor executorInstance;
                                try {
                                    executorInstance = (CommandExecutor) clazz.getConstructor(PluginSimplifier.class).newInstance(plugin);
                                } catch (NoSuchMethodException e) {
                                    executorInstance = (CommandExecutor) clazz.getConstructor().newInstance();
                                }

                                for (Method method : clazz.getDeclaredMethods()) {
                                    if (method.isAnnotationPresent(Register.class)) {
                                        Register registerAnnotation = method.getAnnotation(Register.class);
                                        String commandName = registerAnnotation.value();

                                        if (plugin.getCommand(commandName) != null) {
                                            Objects.requireNonNull(plugin.getCommand(commandName)).setExecutor(executorInstance);

                                            if (hasCompleterAnnotation) {
                                                Completer completerAnnotation = method.getAnnotation(Completer.class);
                                                TabCompleter completer = completerAnnotation.value().newInstance();
                                                Objects.requireNonNull(plugin.getCommand(commandName)).setTabCompleter(completer);
                                            }
                                        } else {
                                            plugin.getLogger().log(Level.WARNING, "Command not found in plugin.yml: " + commandName);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to register commands.", e);
        }
    }
}