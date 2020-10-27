package com.thur.btc.DB;

import com.thur.btc.Main;
import org.bukkit.Bukkit;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Connect {
    public static Connection con = null;
    private static final Main instance = Main.getInstance();
    public static void openConnectionMySQL() throws SQLException {
        if(instance.getConfig().getBoolean("mysql.active")){
            String HOST = instance.getConfig().getString("mysql.host");
            Integer PORT = instance.getConfig().getInt("mysql.port");
            String PASS = instance.getConfig().getString("mysql.pass");
            String USER = instance.getConfig().getString("mysql.user");
            String DB = instance.getConfig().getString("mysql.db");

            String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB;
            try{
                con = DriverManager.getConnection(URL, USER, PASS);
                for(int i = 0; i < 5; i++) Bukkit.getConsoleSender().sendMessage("§7[TH_BTC]");
                Bukkit.getConsoleSender().sendMessage("§a[TH_BTC]   __   __  __   __  ____     ___    _");
                Bukkit.getConsoleSender().sendMessage("§a[TH_BTC]   |  \\/  | \\ \\ / / / ___|   / _ \\  | |    ");
                Bukkit.getConsoleSender().sendMessage("§a[TH_BTC]   | |\\/| |  \\ V /  \\___ \\  | | | | | |    ");
                Bukkit.getConsoleSender().sendMessage("§a[TH_BTC]   | |  | |   | |    ___) | | |_| | | |___");
                Bukkit.getConsoleSender().sendMessage("§a[TH_BTC]   |_|  |_|   |_|   |____/   \\__\\_\\ |_____|");
                Bukkit.getConsoleSender().sendMessage("§a[TH_BTC]      Conexão com o MYSQL sucedida.");
                Bukkit.getConsoleSender().sendMessage("§a[TH_BTC]    Inicializacao completa com sucesso.");
                PreparedStatement stm = con.prepareStatement("CREATE TABLE IF NOT EXISTS players (ID INT(11) UNSIGNED AUTO_INCREMENT PRIMARY KEY, UUID VARCHAR(110) NOT NULL, NAME VARCHAR(110) NOT NULL, BTC REAL)");
                stm.execute();
            }catch(SQLException e){
                for(int i = 0; i < 5; i++) Bukkit.getConsoleSender().sendMessage("§7[TH_BTC]");
                e.printStackTrace();
                Bukkit.getConsoleSender().sendMessage("§c[TH_BTC]   __   __  __   __  ____     ___    _");
                Bukkit.getConsoleSender().sendMessage("§c[TH_BTC]   |  \\/  | \\ \\ / / / ___|   / _ \\  | |    ");
                Bukkit.getConsoleSender().sendMessage("§c[TH_BTC]   | |\\/| |  \\ V /  \\___ \\  | | | | | |    ");
                Bukkit.getConsoleSender().sendMessage("§c[TH_BTC]   | |  | |   | |    ___) | | |_| | | |___");
                Bukkit.getConsoleSender().sendMessage("§c[TH_BTC]   |_|  |_|   |_|   |____/   \\__\\_\\ |_____|");
                Bukkit.getConsoleSender().sendMessage("§c[TH_BTC]      Erro ao conectar-se ao MYSQL.");
                Bukkit.getConsoleSender().sendMessage("§c[TH_BTC]         Alterando para SQLite.");
                openConnectionSQLite();
            }
        }
    }

    public static void openConnectionSQLite() throws SQLException {
        File file = new File(instance.getDataFolder(), "storage.db");
        String URL = "jdbc:sqlite:" + file;
        try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(URL);
            for(int i = 0; i < 5; i++) Bukkit.getConsoleSender().sendMessage("§7[PandoraAntisk]");
            Bukkit.getConsoleSender().sendMessage("§a[PandoraAntisk]     ____     ___    _       ___   _____   _____ ");
            Bukkit.getConsoleSender().sendMessage("§a[PandoraAntisk]    / ___|   / _ \\  | |     |_ _| |_   _| | ____|");
            Bukkit.getConsoleSender().sendMessage("§a[PandoraAntisk]    \\___ \\  | | | | | |      | |    | |   |  _|  ");
            Bukkit.getConsoleSender().sendMessage("§a[PandoraAntisk]     ___) | | |_| | | |___   | |    | |   | |___ ");
            Bukkit.getConsoleSender().sendMessage("§a[PandoraAntisk]    |____/   \\__\\_\\ |_____| |___|   |_|   |_____|");
            Bukkit.getConsoleSender().sendMessage("§a[PandoraAntisk]             Conexão com o SQLite sucedida.");
            Bukkit.getConsoleSender().sendMessage("§a[PandoraAntisk]          Inicializacao completa com sucesso.");
            PreparedStatement stm = con.prepareStatement("CREATE TABLE IF NOT EXISTS `region` (`X` TEXT,`Y` TEXT,`Z` TEXT, `WORLD` TEXT");
            stm.execute();
        }catch(Exception e){
            e.printStackTrace();
            for(int i = 0; i < 5; i++) Bukkit.getConsoleSender().sendMessage("§7[PandoraAntisk]");
            Bukkit.getConsoleSender().sendMessage("§c[PandoraAntisk]     ____     ___    _       ___   _____   _____ ");
            Bukkit.getConsoleSender().sendMessage("§c[PandoraAntisk]    / ___|   / _ \\  | |     |_ _| |_   _| | ____|");
            Bukkit.getConsoleSender().sendMessage("§c[PandoraAntisk]    \\___ \\  | | | | | |      | |    | |   |  _|  ");
            Bukkit.getConsoleSender().sendMessage("§c[PandoraAntisk]     ___) | | |_| | | |___   | |    | |   | |___ ");
            Bukkit.getConsoleSender().sendMessage("§c[PandoraAntisk]    |____/   \\__\\_\\ |_____| |___|   |_|   |_____|");
            Bukkit.getConsoleSender().sendMessage("§c[PandoraAntisk]             Erro ao conectar-se ao SQLite.");
            Bukkit.getConsoleSender().sendMessage("§c[PandoraAntisk]                 Desabilitando plugin.");
            instance.getPluginLoader().disablePlugin(instance);
            closeConnection();
        }
    }

    public static void closeConnection(){
        if(con != null){
            try{
                con.close();
            }catch(SQLException e){

            }
        }
    }

    public static void openConnection(){
        if(instance.getConfig().getBoolean("mysql.active")){
            try{
                openConnectionMySQL();
            }catch(SQLException e){

            }
        }else{
            try{
                openConnectionSQLite();
            }catch(SQLException e){

            }
        }
    }
}
