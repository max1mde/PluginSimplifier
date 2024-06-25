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
        pluginInstance = this;
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

    protected void onStart() {

    }

    protected void onStop() {

    }

    private void initializeInstance() {
        for (Field field : getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(MainClassInstance.class)) {
                if (PluginSimplifier.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    try {
                        field.set(this, this);
                    } catch (IllegalAccessException e) {
                        getLogger().severe("Could not initialize field annotated with @MainClassInstance: " + field.getName());
                        e.printStackTrace();
                    }
                } else {
                    getLogger().warning("Field annotated with @MainClassInstance must be of type PluginSimplifier: " + field.getName());
                }
            }
        }
    }
}