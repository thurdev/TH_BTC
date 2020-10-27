package com.thur.btc.Help;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Help_CMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            for(int i = 0; i < 50; i++){
                p.sendMessage("");
            }
            p.sendMessage("§a§lTH_BTC §7- §eComandos");
            p.sendMessage(" ");
            if(p.hasPermission("thbtc.admin")){
                p.sendMessage("§a/btc §7- §eMostra seus BTC's");
                p.sendMessage("§a/bolsa §7- §eAbre a bolsa de valores (pode comprar ou vender btc's).");
                p.sendMessage("§a/blackmarket | /bm §7- §eAbre o mercado negro.");
                p.sendMessage("§a/btctop §7- §eMostra lista de jogadores com mais BTC's.");
                p.sendMessage("§4/addbtc <player> [BTC] §7- §cAdiciona uma quantia de btc's a um player.");
                p.sendMessage("§4/setbtc <player> [BTC] §7- §cAdiciona uma quantia de btc's a um player.");
                p.sendMessage("§4/removebtc <player> [BTC] §7- §cRemove uma quantia de btc's a um player.");
                p.sendMessage("§4/btcboost <player> [BOOSTER_ID] §7- §cDe um booster a um jogador.");
            }else{
                p.sendMessage("§a/btc §7- §eMostra seus BTC's");
                p.sendMessage("§a/bolsa §7- §eAbre a bolsa de valores (pode comprar ou vender btc's).");
                p.sendMessage("§a/blackmarket | /bm §7- §eAbre o mercado negro.");
                p.sendMessage("§a/btctop §7- §eMostra lista de jogadores com mais BTC's.");
            }
        }
        return false;
    }
}
