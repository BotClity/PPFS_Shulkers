package com.ppfs.skulkers.Util.Menu.Events.Menu;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public interface MenuOnInteract {
    boolean run(Action action);

    record Action(
            InventoryView view,
            HumanEntity player,
            Inventory clicked,
            int slot
            ){
    }
}
