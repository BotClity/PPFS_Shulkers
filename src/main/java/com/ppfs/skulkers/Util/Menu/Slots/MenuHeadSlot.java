package com.ppfs.skulkers.Util.Menu.Slots;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;


public class MenuHeadSlot extends MenuSlot {
    public void setHeadValue(String value){
        ItemMeta meta = getItem().getItemMeta();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "");
        gameProfile.getProperties().put("textures", new Property("textures", value));
        if (meta!= null){
            Field field;
            try{
                field = meta.getClass().getDeclaredField("profile");
                field.setAccessible(true);
                field.set(meta, gameProfile);
            }catch (IllegalAccessException | NoSuchFieldException e){
                Bukkit.getLogger().warning("Failed to set head value");
            }
        }
        getItem().setItemMeta(meta);
    }
    public void setHeadOwner(OfflinePlayer player){
        SkullMeta meta = (SkullMeta) getItem().getItemMeta();
        meta.setOwningPlayer(player);
        getItem().setItemMeta(meta);
    }
}
