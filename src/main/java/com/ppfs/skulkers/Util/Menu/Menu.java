package com.ppfs.skulkers.Util.Menu;

import com.ppfs.skulkers.Util.Menu.Events.Menu.MenuOnClick;
import com.ppfs.skulkers.Util.Menu.Events.Menu.MenuOnClose;
import com.ppfs.skulkers.Util.Menu.Events.Menu.MenuOnDrop;
import com.ppfs.skulkers.Util.Menu.Slots.MenuSlot;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Setter
@Getter
public class Menu {
    private final String title;
    private final String id;
    private Inventory inventory;
    private HashMap<Integer, MenuSlot> slots;
    private boolean interactDisabled = true;
    private MenuOnClose onClose;
    private MenuOnClick click;
    private MenuOnDrop onDrop;
    private List<HumanEntity> viewers;

    public Menu(String id, String title, int rows) {
        this.id = id;
        this.title = title;
        slots = new HashMap<>();
        inventory = Bukkit.createInventory(null, rows, title);
        viewers = new ArrayList<>();
    }

    public Menu(String id, String title, int rows, InventoryHolder holder) {
        this.id = id;
        this.title = title;
        slots = new HashMap<>();
        inventory = Bukkit.createInventory(holder, rows, title);
        viewers = new ArrayList<>();

    }

    @Deprecated
    public void setInventory(Inventory inventory) {
        slots.clear();
        this.inventory = inventory;
        viewers = new ArrayList<>();

    }

    public MenuSlot getSlot(int slot) {
        return slots.get(slot);
    }

    public Menu addSlot(int position, MenuSlot slot) {
        slot.setPosition(position);
        inventory.setItem(position, slot.getItem());
        slots.put(position, slot);
        return this;
    }

    public Menu addSlot(MenuSlot slot) {
        int pos = slot.getPosition();
        if (pos == -1) pos = inventory.firstEmpty();
        if (pos == -1) return this;
        addSlot(pos, slot);
        return this;
    }

    public void open(HumanEntity player) {
        player.openInventory(inventory);
        viewers.add(player);
        MenuManager.getInstance().registerMenu(this);
    }

    public void close(Player player) {
        if (inventory.getViewers().contains(player)) {
            player.closeInventory();
            viewers.remove(player);
        }
    }

    public void open(String name) {
        Player player = Bukkit.getPlayer(name);
        if (player == null) return;
        open(player);
    }

    public boolean hasOnClose() {
        return onClose != null;
    }

    public boolean hasOnClick() {
        return click != null;
    }

    public boolean hasOnDrop() {
        return onDrop != null;
    }

}
