package com.ppfs.skulkers.Util.Menu.Slots;

import com.ppfs.skulkers.Util.Menu.Events.Slot.SlotListener;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@Getter
@Setter
public class MenuSlot {

    private ItemStack item;
    private int position = -1;
    private SlotListener listener;
    private boolean interactDisabled = true;

    public MenuSlot setSlotListener(SlotListener listener) {
        this.listener = listener;
        return this;
    }

    public MenuSlot setInteractDisabled(boolean interactDisabled) {
        this.interactDisabled = interactDisabled;
        return this;
    }

    public boolean hasListener(){
        return listener != null;
    }

    public MenuSlot setLore(Component... lore) {
        List<Component> components = List.of(lore);
        item.lore(components);
        return this;
    }

    public MenuSlot setLore(String... lore) {
        List<String> list = List.of(lore);
        item.setLore(list);
        return this;
    }

    public MenuSlot setLore(List<String> lore) {
        item.setLore(lore);
        return this;
    }

    public MenuSlot setDisplayName(String displayName) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        return this;
    }

    public MenuSlot hideAttributes(boolean hideAttributes) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;
        if (hideAttributes) {
            meta.addItemFlags(
                    ItemFlag.HIDE_ATTRIBUTES,
                    ItemFlag.HIDE_ENCHANTS,
                    ItemFlag.HIDE_UNBREAKABLE
            );
        }else {
            meta.removeItemFlags(
                    ItemFlag.HIDE_ATTRIBUTES,
                    ItemFlag.HIDE_ENCHANTS,
                    ItemFlag.HIDE_UNBREAKABLE
            );
        }
        item.setItemMeta(meta);
        return this;
    }

    public MenuSlot setMaterial(Material material) {
        item.setType(material);
        return this;
    }
}

