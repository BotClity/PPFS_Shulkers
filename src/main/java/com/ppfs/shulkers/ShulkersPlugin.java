package com.ppfs.shulkers;

import com.ppfs.shulkers.Listener.ClickListener;
import com.ppfs.shulkers.Service.LocalizeService;
import com.ppfs.shulkers.Service.ShulkerService;
import com.ppfs.shulkers.Util.Menu.MenuManager;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.node.types.PermissionNode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public final class ShulkersPlugin extends JavaPlugin {
    @Getter
    private static ShulkersPlugin instance;
    @Getter
    private static final StateFlag OPEN_SHULKERS = new StateFlag("open-shulkers", true);

    @Override
    public void onLoad() {
        WorldGuard.getInstance().getFlagRegistry().register(OPEN_SHULKERS);
    }

    @Override
    public void onEnable() {
        saveConfig();
        // Plugin startup logic
        instance = this;

        registerEvents();

        LocalizeService.getInstance();
        MenuManager.getInstance();
        ShulkerService.getInstance();

        LuckPerms luckPerms = LuckPermsProvider.get();

        PermissionNode permission = PermissionNode.builder()
                .permission("shulkers.open")
                .value(true)
                .build();
        boolean perm = getConfig().getBoolean("shulker-permission.default");
        permission = permission.toBuilder().value(perm).build();
        Group group = luckPerms.getGroupManager().getGroup("default");
        group.data().add(permission);
        luckPerms.getGroupManager().saveGroup(group);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new ClickListener(), instance);
    }

    public boolean hasFlag(Player player) {
        World world = BukkitAdapter.adapt(player.getWorld());
        BlockVector3 vector3 = BukkitAdapter.asBlockVector(player.getLocation());
        RegionManager regionManager = WorldGuard.getInstance()
                .getPlatform()
                .getRegionContainer()
                .get(
                        world
                );
        Set<ProtectedRegion> regions =
                regionManager.getApplicableRegions(
                                vector3
                        )
                        .getRegions();
        if (regions.isEmpty()) {
            return regionManager.getRegion("__global__").getFlag(OPEN_SHULKERS) == StateFlag.State.ALLOW;
        }
        for (ProtectedRegion region : regions) {
            if (region.getFlag(OPEN_SHULKERS) == StateFlag.State.ALLOW) return true;
        }
        return false;
    }


}
