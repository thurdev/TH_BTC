package com.thur.btc.Booster;

import com.thur.btc.Object.Booster;
import com.thur.btc.Utils.Global;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckTimer implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(Global.boostCountdown.containsKey(p.getUniqueId())){
                Booster booster = Global.hasBoostActive.get(p.getUniqueId());
                p.sendMessage("§aVocê possui um booster '" + booster.getMultiply() + "x' por '" + Global.boostCountdown.get(p.getUniqueId()) + "s'");
            }else{
                p.sendMessage("§cVocê não possui nenhum booster ativo.");
            }
        }
        return false;
    }
}
