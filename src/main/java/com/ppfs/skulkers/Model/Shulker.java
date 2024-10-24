package com.ppfs.skulkers.Model;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public record Shulker(
        Player player,
        int slot,
        ItemStack item
) {
}
