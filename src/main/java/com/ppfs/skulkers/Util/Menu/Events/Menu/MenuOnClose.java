package com.ppfs.skulkers.Util.Menu.Events.Menu;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

public interface MenuOnClose {
    boolean run(Action action);

    record Action(
            HumanEntity player,
            Inventory inventory
            ){
    }
}
