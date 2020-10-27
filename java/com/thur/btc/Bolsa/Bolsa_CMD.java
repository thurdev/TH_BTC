package com.thur.btc.Bolsa;

import com.thur.btc.API.BTC;
import com.thur.btc.Main;
import com.thur.btc.Managers.PlayerManager;
import com.thur.btc.Utils.Heads;
import com.thur.btc.Utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Bolsa_CMD implements CommandExecutor {

    private final Main instance = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            openBolsaMenu(p);
        }
        return false;
    }

    public void openBolsaMenu(Player player){
        Inventory inventory = Bukkit.createInventory(null, 3*9, "§bBolsa de Valores");
        String currency_symbol = BTC.getSymbol(instance.getConfig().getString("btc_options.currency"));
        for(Heads head: Heads.values()){
            if(head.getName().equals("Comprar")){
                ItemStack is = new ItemStack(Material.STAINED_CLAY, 1, (short) 5);
                ItemMeta meta = is.getItemMeta();
                meta.setDisplayName("§aComprar BTC");

                Double buy_value = BTC.getBuyValue(instance.getConfig().getString("btc_options.currency"));

                List<String> lore = new ArrayList<String>();
                lore.add("§r ");
                lore.add("§a » §7Clique para comprar");
                lore.add("§a » §7Valor de Compra Atual: §a" + currency_symbol  + String.format("%.2f", buy_value));
                lore.add("§r ");
                meta.setLore(lore);

                is.setItemMeta(meta);
                inventory.setItem(11, is);

            }else if(head.getName().equals("Vender")){
                ItemStack is = new ItemStack(Material.STAINED_CLAY, 1, (short) 14);
                ItemMeta meta = is.getItemMeta();
                meta.setDisplayName("§cVender BTC");

                Double sell_value = BTC.getSellValue(instance.getConfig().getString("btc_options.currency"));

                List<String> lore = new ArrayList<String>();
                lore.add("§r ");
                lore.add("§c » §7Clique para vender tudo.");
                lore.add("§c » §7Valor de Venda Atual: §c" + currency_symbol + String.format("%.2f", sell_value));
                lore.add("§r ");
                meta.setLore(lore);


                is.setItemMeta(meta);
                inventory.setItem(15, is);
            }
            ItemStack is = Item.getPlayerHead(player.getDisplayName(), "§7" + player.getDisplayName());
            ItemMeta meta = is.getItemMeta();
            List<String> lore = new ArrayList<String>();
            lore.add("§r ");
            Double player_btc = PlayerManager.getBTC(player.getUniqueId());
            lore.add("§a » §7Quantidade Atual: §6" + String.format("%.10f", player_btc) + "BTC");
            Double sell_value = BTC.getSellValue(instance.getConfig().getString("btc_options.currency"));
            Double final_value = player_btc * sell_value;
            lore.add("§a » §7Valor de Venda: §a" + currency_symbol + String.format("%.2f", final_value));
            lore.add("§r ");
            meta.setLore(lore);
            is.setItemMeta(meta);
            inventory.setItem(13, is);
        }
        //tocarSom(player);
        player.openInventory(inventory);
    }

}
