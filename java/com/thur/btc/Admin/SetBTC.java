package com.thur.btc.Admin;

import com.thur.btc.Managers.PlayerManager;
import com.thur.btc.Utils.Global;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SetBTC implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("thbtc.admin")){
                if(args.length == 2){
                    String player_name = args[0];
                    Double btcs = Double.valueOf(args[1]);
                    if(Global.isOnline(player_name, p)){
                        Player onlinePlayer = Global.getPlayer(p);
                        PlayerManager.setBTC(onlinePlayer.getUniqueId(), btcs);
                        p.sendMessage("§a'" + onlinePlayer.getDisplayName() + "' BTC's foi definido para '" + btcs + "'");
                    }else{
                        p.sendMessage("§cEste jogador esta offline.");
                    }
                }else{
                    p.sendMessage("§cUtilize: /setbtc <player> [btc]");
                }
            }
        }
        return false;
    }
}
