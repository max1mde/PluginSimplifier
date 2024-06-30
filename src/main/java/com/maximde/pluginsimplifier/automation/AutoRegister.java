package com.maximde.pluginsimplifier.automation;

import com.maximde.pluginsimplifier.PluginHolder;
import com.maximde.pluginsimplifier.PluginSimplifier;
import com.maximde.pluginsimplifier.annotations.Completer;
import com.maximde.pluginsimplifier.annotations.Register;
import com.maximde.pluginsimplifier.command.CommandRegistrar;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;

public class AutoRegister {

    private static final PluginSimplifier plugin = PluginHolder.getPluginInstance();
    private static final CommandRegistrar commandRegistrar = new CommandRegistrar();

    /**
     * Registers all commands with @Register annotation
     * As well as all classes which implement Listener
     * Which are found in the package/subpackages.
     *
     * @param packageNames the package names to scan for commands and events
     */
    public static void registerAll(String... packageNames) {
        registerCommands(packageNames);
        registerEvents(packageNames);
    }

    /**
     * Registers all commands with @Register annotation found in the package/subpackages.
     *
     * @param packageNames the package names to scan for commands
     */
    public static void registerCommands(String... packageNames) {
        processClasses(packageNames, AutoRegister::registerCommand);
    }

    /**
     * Registers all classes which implement Listener found in the package/subpackages.
     *
     * @param packageNames the package names to scan for events
     */
    public static void registerEvents(String... packageNames) {
        processClasses(packageNames, AutoRegister::registerEvent);
    }

    private static void processClasses(String[] packageNames, ClassProcessor processor) {
        try {
            URLClassLoader classLoader = (URLClassLoader) plugin.getClass().getClassLoader();
            URL jarUrl = classLoader.getURLs()[0];
            File jarFile = new File(jarUrl.toURI());

            try (JarFile jar = new JarFile(jarFile)) {
                Enumeration<JarEntry> entries = jar.entries();

                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().endsWith(".class")) {
                        String className = entry.getName().replace('/', '.').replace(".class", "");

                        if (matchesPackage(className, packageNames)) {
                            try {
                                Class<?> clazz = Class.forName(className);
                                processor.process(clazz);
                            } catch (NoClassDefFoundError | ClassNotFoundException ignored) {
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to process classes.", e);
        }
    }

    private static boolean matchesPackage(String className, String[] packageNames) {
        for (String packageName : packageNames) {
            if (className.startsWith(packageName.replace('/', '.'))) {
                return true;
            }
        }
        return false;
    }

    private static void registerCommand(Class<?> clazz) {
        if (!CommandExecutor.class.isAssignableFrom(clazz)) {
            return;
        }

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Register.class)) {
                Register registerAnnotation = method.getAnnotation(Register.class);
                String commandName = registerAnnotation.name();
                String description = registerAnnotation.description();
                String aliases = registerAnnotation.aliases();
                String permission = registerAnnotation.permission();

                CommandExecutor executorInstance = createExecutorInstance(clazz);
                if (executorInstance == null) {
                    continue;
                }

                commandRegistrar.registerCommand(commandName, executorInstance, description, aliases, permission);

                if (method.isAnnotationPresent(Completer.class)) {
                    registerCompleter(method, commandName);
                }
            }
        }
    }

    private static CommandExecutor createExecutorInstance(Class<?> clazz) {
        if (clazz.isInstance(plugin)) {
            return plugin;
        }
        try {
            return (CommandExecutor) clazz.getConstructor().newInstance();
        } catch (Exception ex) {
            plugin.getLogger().log(Level.SEVERE, "Failed to create instance of " + clazz.getName(), ex);
            return null;
        }
    }

    private static void registerCompleter(Method method, String commandName) {
        try {
            Completer completerAnnotation = method.getAnnotation(Completer.class);
            TabCompleter completer = completerAnnotation.value().newInstance();
            Objects.requireNonNull(plugin.getCommand(commandName)).setTabCompleter(completer);
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to register tab completer for command: " + commandName, e);
        }
    }

    private static void registerEvent(Class<?> clazz) {
        if (Listener.class.isAssignableFrom(clazz)) {
            try {
                Listener listenerInstance = (Listener) clazz.getConstructor().newInstance();
                plugin.getServer().getPluginManager().registerEvents(listenerInstance, plugin);
            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to register event listener: " + clazz.getName(), e);
            }
        }
    }

    @FunctionalInterface
    private interface ClassProcessor {
        void process(Class<?> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException;
    }
}