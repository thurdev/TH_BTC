package com.thur.btc.BTC;

import com.thur.btc.API.BTC;
import com.thur.btc.DB.Connect;
import com.thur.btc.Main;
import com.thur.btc.Managers.PlayerManager;
import com.thur.btc.Utils.Heads;
import com.thur.btc.Utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BTC_CMD implements CommandExecutor {

    private final Main instance = Main.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(instance.getConfig().getBoolean("btc_cmd.permission")){
                if(p.hasPermission("thbtc.btc")){
                    openBTCMenu(p);
                }else{
                    p.sendMessage("§cVocê não tem permissão para executar este comando.");
                }
            }else{
                openBTCMenu(p);
            }
        }
        return false;
    }

    private void openBTCMenu(Player player){
        Inventory inv = Bukkit.createInventory(null, 3*9, "§eBTC Info");
        String currency_symbol = BTC.getSymbol(instance.getConfig().getString("btc_options.currency"));
            ItemStack is = Item.getPlayerHead(player.getDisplayName(), "§7" + player.getDisplayName());
            ItemMeta meta = is.getItemMeta();
            List<String> lore = new ArrayList<String>();
            lore.add("§r ");
            lore.add("§a » §7Quantidade Atual: §6" + String.format("%.10f", PlayerManager.getBTC(player.getUniqueId())) + "BTC");
            Double sell_value = BTC.getSellValue(instance.getConfig().getString("btc_options.currency"));
            Double final_value = PlayerManager.getBTC(player.getUniqueId()) * sell_value;
            lore.add("§a » §7Valor de Venda: §a" + currency_symbol + String.format("%.2f", final_value));
            lore.add("§r ");
            meta.setLore(lore);
            is.setItemMeta(meta);
            inv.setItem(13, is);
        player.openInventory(inv);
    }

}
