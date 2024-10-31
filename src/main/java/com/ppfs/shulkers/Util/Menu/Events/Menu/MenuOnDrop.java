package com.ppfs.shulkers.Util.Menu.Events.Menu;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public interface MenuOnDrop {
    boolean run(Action action);
    record Action(
            Item item,
            Player player
    ){
    }
}
