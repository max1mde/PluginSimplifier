package com.maximde.pluginsimplifier;

import com.maximde.pluginsimplifier.annotations.MainClassInstance;
import com.maximde.pluginsimplifier.commands.CommandRegistry;
import com.maximde.pluginsimplifier.events.EventsRegistry;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public abstract class PluginSimplifier extends JavaPlugin {

    @Getter
    private static PluginSimplifier pluginInstance;

    @Override
    public void onEnable() {
        initializeInstance();

        try {
            getClass().getDeclaredMethod("onStart");
            onStart();
        } catch (NoSuchMethodException ignored) {

        }

        CommandRegistry.registerCommands();
        EventsRegistry.registerEvents();
    }

    @Override
    public void onDisable() {
        try {
            getClass().getDeclaredMethod("onStop");
            onStop();
        } catch (NoSuchMethodException ignored) {

        }
    }

    /**
     * Optional method to be overridden by subclasses.
     * Called automatically during plugin startup after initialization.
     */
    protected void onStart() {

    }

    /**
     * Optional method to be overridden by subclasses.
     * Called automatically during plugin shutdown.
     */
    protected void onStop() {

    }

    /**
     * Initializes fields annotated with @MainClassInstance.
     * These fields must be of type PluginSimplifier or its subclass.
     * This method is called during plugin initialization to inject the plugin instance into annotated fields.
     */
    private void initializeInstance() {
        for (Field field : getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(MainClassInstance.class)) {
                if (PluginSimplifier.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    try {
                        field.set(this, this);
                    } catch (IllegalAccessException e) {
                        getLogger().severe("Failed to initialize @MainClassInstance field: " + field.getName());
                        e.printStackTrace();
                    }
                } else {
                    getLogger().warning("Field annotated with @MainClassInstance must be of type PluginSimplifier: " + field.getName());
                }
            }
        }
    }
}