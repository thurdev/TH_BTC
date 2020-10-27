package com.thur.btc;

import com.thur.btc.API.License;
import com.thur.btc.Booster.BOOST_CMD;
import com.thur.btc.Admin.AddBTC;
import com.thur.btc.Admin.RemoveBTC;
import com.thur.btc.Admin.SetBTC;
import com.thur.btc.BTC.BTC_CMD;
import com.thur.btc.BlackMarket.BM_CMD;
import com.thur.btc.Bolsa.Bolsa_CMD;
import com.thur.btc.Booster.CheckTimer;
import com.thur.btc.DB.Connect;
import com.thur.btc.Events.*;
import com.thur.btc.Help.Help_CMD;
import com.thur.btc.TOP.BTCTOP_CMD;
import com.thur.btc.Utils.Config;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Main extends JavaPlugin {

    public static Config config;

    private ArrayList<Main> invalid_key = new ArrayList<Main>();

    @Override
    public void onLoad() {
        config = new Config(this, "config.yml");
        if(License.verifyLicense(getConfig().getString("LICENSE_KEY"))){
            Bukkit.getConsoleSender().sendMessage("§a[TH_BTC]");
            Bukkit.getConsoleSender().sendMessage("§a[TH_BTC]   License Key: OK !");
            Bukkit.getConsoleSender().sendMessage("§a[TH_BTC]");
            loadConfig(config);
            Bukkit.getConsoleSender().sendMessage("§9[TH_BTC]");
            Bukkit.getConsoleSender().sendMessage("§9[TH_BTC]     _____   _   _           ____    _____    ____ ");
            Bukkit.getConsoleSender().sendMessage("§9[TH_BTC]    |_   _| | | | |         | __ )  |_   _|  / ___|");
            Bukkit.getConsoleSender().sendMessage("§9[TH_BTC]      | |   | |_| |         |  _ \\    | |   | |    ");
            Bukkit.getConsoleSender().sendMessage("§9[TH_BTC]      | |   |  _  |         | |_) |   | |   | |___");
            Bukkit.getConsoleSender().sendMessage("§9[TH_BTC]      |_|   |_| |_|  _____  |____/    |_|    \\____|");
            Bukkit.getConsoleSender().sendMessage("§9[TH_BTC]                    |_____|");
            Bukkit.getConsoleSender().sendMessage("§9[TH_BTC]     By: www.github.com/thurdev - V 1.0 Release Build");
            Bukkit.getConsoleSender().sendMessage("§9[TH_BTC]        Inicializando tarefas para inicializacao...");
            Bukkit.getConsoleSender().sendMessage("§9[TH_BTC]");
        }else{
            Bukkit.getConsoleSender().sendMessage("§c[TH_BTC]");
            Bukkit.getConsoleSender().sendMessage("§c[TH_BTC]   License Key: ERROR !");
            Bukkit.getConsoleSender().sendMessage("§c[TH_BTC]  Verify your license key");
            Bukkit.getConsoleSender().sendMessage("§c[TH_BTC]");
            invalid_key.add(this);
        }
    }

    @Override
    public void onEnable() {
        if(invalid_key.contains(this)){
            try {
                Thread.sleep(1000);
                Bukkit.getPluginManager().disablePlugin(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            Connect.openConnection();
            RegisterCommands();
            RegisterEvents();
            setupChat();
            setupEconomy();
            setupPermissions();
        }
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
        Bukkit.getConsoleSender().sendMessage("§c[TH_BTC] Desabilitando Plugin....");
        Bukkit.getConsoleSender().sendMessage("§c[TH_BTC] Plugin Desabilitado.");
        Connect.closeConnection();
    }

    private void RegisterCommands(){
        getCommand("blackmarket").setExecutor(new BM_CMD());
        getCommand("btc").setExecutor(new BTC_CMD());
        getCommand("bolsa").setExecutor(new Bolsa_CMD());
        getCommand("setbtc").setExecutor(new SetBTC());
        getCommand("addbtc").setExecutor(new AddBTC());
        getCommand("removebtc").setExecutor(new RemoveBTC());
        getCommand("btchelp").setExecutor(new Help_CMD());
        getCommand("btctop").setExecutor(new BTCTOP_CMD());
        getCommand("btcboost").setExecutor(new BOOST_CMD());
        getCommand("boost").setExecutor(new CheckTimer());
    }

    private void RegisterEvents(){
        Bukkit.getPluginManager().registerEvents(new RegisterPlayer(), this);
        Bukkit.getPluginManager().registerEvents(new BTCGui_ClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new BMGui_ClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new BolsaGui_ClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ConfirmSellBTC(), this);
        Bukkit.getPluginManager().registerEvents(new TOPGui_ClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MineBTC(), this);
        Bukkit.getPluginManager().registerEvents(new UseBooster(), this);
        Bukkit.getPluginManager().registerEvents(new ConfirmBuyBTC(), this);
    }

    public static Main getInstance() {
        return (Main) Bukkit.getPluginManager().getPlugin("TH_BTC");
    }

    private void loadConfig(Config input){
        if(!input.getFile().exists()) input.saveDefaultConfig();
        input.reloadConfig();
    }

    public static Permission permission = null;
    public static Economy economy = null;
    public static Chat chat = null;

    public static Economy getEconomy(){
        return economy;
    }

    private boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    private boolean setupChat()
    {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
}
