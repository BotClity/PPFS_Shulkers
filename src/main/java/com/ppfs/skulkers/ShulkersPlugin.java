package com.ppfs.skulkers;

import com.ppfs.skulkers.Listener.ClickListener;
import com.ppfs.skulkers.Service.LocalizeService;
import com.ppfs.skulkers.Service.ShulkerService;
import com.ppfs.skulkers.Util.Menu.MenuManager;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShulkersPlugin extends JavaPlugin {
    @Getter
    private static ShulkersPlugin instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        registerEvents();

        LocalizeService.getInstance();
        MenuManager.getInstance();
        ShulkerService.getInstance();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new ClickListener(), instance);
    }
}
