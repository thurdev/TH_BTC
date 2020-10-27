package com.thur.btc.BlackMarket;

import com.thur.btc.API.BTC;
import com.thur.btc.API.Requests;
import com.thur.btc.Main;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BM_CMD implements CommandExecutor {

    private final Main instance = Main.getInstance();
    FileConfiguration config = instance.config.getConfig();

    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player){
            Player p = ((Player) sender);
            if(config.getBoolean("black_market.permission")){
                if(p.hasPermission("thbtc.blackmarket")){
                    openMenu(p);
                }else{
                    p.sendMessage("§cVocê não tem permissão para executar este comando.");
                }
            }else{
                openMenu(p);
            }

        }
        return false;
    }

    private void openMenu(Player p){
        Inventory inventory = Bukkit.createInventory(null, 6*9, ChatColor.translateAlternateColorCodes('&', config.getString("black_market.name")));
        for(String itens_count: config.getConfigurationSection("black_market.itens").getKeys(false)){
            Material item_material = Material.getMaterial(config.getString("black_market.itens." + itens_count + ".material"));
            ItemStack item = new ItemStack(item_material);
            ItemMeta meta = item.getItemMeta();
            String item_name = ChatColor.translateAlternateColorCodes('&', config.getString("black_market.itens." + itens_count + ".name"));
            meta.setDisplayName(item_name);
            double price = config.getDouble("black_market.itens." + itens_count + ".price");
            List<String> lore = new ArrayList<String>();
            Double actual_value = BTC.getValue(config.getString("btc_options.currency"));
            Double converted_value = price * actual_value;
            for(String lore_list: config.getStringList("black_market.itens." + itens_count + ".lore")) {
                lore.add(ChatColor.translateAlternateColorCodes('&', lore_list)
                .replace("{price}", String.format("%.10f",price) + " BTC")
                .replace("{converted_price}", String.format("%.5f", converted_value))
                .replace("{currency_symbol}", BTC.getSymbol(config.getString("btc_options.currency"))));
            }
            meta.setLore(lore);

            if(config.getBoolean("black_market.itens." + itens_count + ".has_enchant")){
                Enchantment ench = Enchantment.DAMAGE_ALL;
                item.addUnsafeEnchantment(ench, 5);
                meta.addEnchant(ench, 5, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

            item.setItemMeta(meta);
            int slot = config.getInt("black_market.itens." + itens_count + ".slot");
            inventory.setItem(slot, item);
            p.openInventory(inventory);
        }
    }
}
