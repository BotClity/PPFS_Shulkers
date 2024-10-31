package com.ppfs.shulkers.Listener;

import com.ppfs.shulkers.Model.Shulker;
import com.ppfs.shulkers.Service.ShulkerService;
import com.ppfs.shulkers.ShulkersPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static com.ppfs.shulkers.Service.ShulkerService.isShulker;

public class ClickListener implements Listener {
    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_AIR) return;
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null || item.getType() == Material.AIR) return;
        if (!isShulker(item))return;
        int slot = getSlot(item, player.getInventory());
        if (slot == -1) return;
        if (!ShulkersPlugin.getInstance().hasFlag(player)) return;
        if (!player.hasPermission("shulkers.open")) return;
        ShulkerService.getInstance().openPlayerShulker(new Shulker(player, slot, item));
    }



    private int getSlot(ItemStack item, Inventory inventory) {
        for (int i = 0; i < 9; i++) {
            ItemStack current = inventory.getItem(i);
            if (current == null) continue;
            if (current.isSimilar(item)) return i;
        }
        return -1;
    }
}
