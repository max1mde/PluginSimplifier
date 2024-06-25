package com.maximde.pluginsimplifier.events;

import com.maximde.pluginsimplifier.PluginSimplifier;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;

public class EventsRegistry {

    public static void registerEvents() {
        PluginSimplifier plugin = PluginSimplifier.getPluginInstance();
        URLClassLoader classLoader = (URLClassLoader) plugin.getClass().getClassLoader();

        try {
            URL jarUrl = classLoader.getURLs()[0];
            File jarFile = new File(jarUrl.toURI());

            try (JarFile jar = new JarFile(jarFile)) {
                Enumeration<JarEntry> entries = jar.entries();
                PluginManager pluginManager = plugin.getServer().getPluginManager();

                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().endsWith(".class")) {
                        String className = entry.getName().replace('/', '.').replace(".class", "");
                        Class<?> clazz = Class.forName(className);

                        if (Listener.class.isAssignableFrom(clazz)) {
                            Listener listenerInstance = (Listener) clazz.getConstructor().newInstance();
                            pluginManager.registerEvents(listenerInstance, plugin);
                        }
                    }
                }
            }
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to register events.", e);
        }
    }
}