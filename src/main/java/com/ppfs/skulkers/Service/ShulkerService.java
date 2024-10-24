package com.ppfs.skulkers.Service;

import com.ppfs.skulkers.Model.Shulker;
import com.ppfs.skulkers.Util.Menu.Menu;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

;

public class ShulkerService {
    private static ShulkerService instance;
    private HashMap<Player, Integer> shulkers = new HashMap<>();

    private ShulkerService() {
    }
    private static void load(){
        instance = new ShulkerService();
    }
    public static ShulkerService getInstance(){
        if(instance == null)load();
        return instance;
    }

    public void openPlayerShulker(Shulker shulker){
        BlockStateMeta meta = ((BlockStateMeta) shulker.item().getItemMeta());
        ShulkerBox shulkerBox = (ShulkerBox) meta.getBlockState();
        ItemMeta itemMeta = shulker.item().getItemMeta();
        TextComponent displayName = (TextComponent) itemMeta.displayName();
        String title = (displayName == null) ?
                LocalizeService.getInstance().getTranslation(shulker.item().translationKey()) :
                displayName.toString();

        Menu menu = new Menu(shulker.player().getName(), title, 27);
        menu.setInteractDisabled(false);
        menu.setInventory(shulkerBox.getInventory());

        menu.setOnClose(action ->  {
                shulkerBox.getInventory().setContents(action.inventory().getContents());
                meta.setBlockState(shulkerBox);
                shulker.item().setItemMeta(meta);
                shulker.player().playSound(shulker.player(), Sound.BLOCK_SHULKER_BOX_CLOSE, 1f, 1f);
                return true;
        });

        menu.setClick((action) -> {
            ItemStack item;
            if (action.hotbar() != -1){
                item = action.view().getBottomInventory().getItem(action.hotbar());
            }
            else
                item = action.clicked().getItem(action.slot());

            if (item==null) return true;
            return !isShulker(item);
        });

        menu.setOnDrop(action ->
            !isShulker(action.item().getItemStack())
        );

        shulkers.put(shulker.player(), shulker.slot());
        menu.open(
                shulker.player()
        );
    }
    public static boolean isShulker(ItemStack item) {
        return switch (item.getType()) {
            case SHULKER_BOX, PURPLE_SHULKER_BOX, RED_SHULKER_BOX, CYAN_SHULKER_BOX, LIGHT_GRAY_SHULKER_BOX,
                 GRAY_SHULKER_BOX, PINK_SHULKER_BOX, LIME_SHULKER_BOX, YELLOW_SHULKER_BOX, LIGHT_BLUE_SHULKER_BOX,
                 MAGENTA_SHULKER_BOX, ORANGE_SHULKER_BOX, BLUE_SHULKER_BOX, BLACK_SHULKER_BOX, BROWN_SHULKER_BOX,
                 GREEN_SHULKER_BOX, WHITE_SHULKER_BOX -> true;
            default -> false;
        };
    }

}
