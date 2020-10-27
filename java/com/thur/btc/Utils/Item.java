package com.thur.btc.Utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Item {
    public static ItemStack createSkull(String url, String name){
        Material type;
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        if(url.length() > 0) return head;

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        UUID uUID;
        headMeta.setDisplayName(name.substring(0, 1).toUpperCase() + name.substring(1));
        headMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        List<String> lore = new ArrayList<String>();
        headMeta.setLore(lore);
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", url));
        try{
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        }catch(IllegalArgumentException error){
            error.printStackTrace();
        }catch(NoSuchFieldException error){
            error.printStackTrace();
        }catch(SecurityException error){
            error.printStackTrace();
        }catch(IllegalAccessException error){
            error.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }


    public static ItemStack getPlayerHead(String player, String itemName){
        boolean isNewVersion = Arrays.stream(Material.values())
                .map(Material::name).collect(Collectors.toList()).contains("PLAYER_HEAD");

        Material type = Material.matchMaterial(isNewVersion ? "PLAYER_HEAD" : "SKULL_ITEM");
        ItemStack item = new ItemStack(type, 1);

        if(!isNewVersion)
            item.setDurability((short) 3);

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(player);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(itemName);
        item.setItemMeta(meta);
        return item;
    }

    public static void removeBoosterInventory(PlayerInventory inv, ItemStack item, int amount) {
        for (ItemStack is : inv.getContents()) {
            if (is != null && is.equals(item)) {
                int newamount = is.getAmount() - amount;
                if (newamount > 0) {
                    is.setAmount(newamount);
                    break;
                } else {
                    inv.remove(is);
                    amount = -newamount;
                    if (amount == 0) break;
                }
            }
        }
    }

}
