package com.thur.btc.API;

import com.thur.btc.Main;
import org.bukkit.Bukkit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class Requests {

    private static final Main instance = Main.getInstance();

    public static JSONObject getApiResponse(){
            try {
                URL url = new URL("https://blockchain.info/ticker");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in .readLine()) != null) {
                    response.append(inputLine);
                } in .close();
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(response.toString());
                return json;
            } catch (MalformedURLException e) {
                Bukkit.getPluginManager().disablePlugin(instance);
                e.printStackTrace();
            } catch (IOException e) {
                Bukkit.getPluginManager().disablePlugin(instance);
                e.printStackTrace();
            } catch (ParseException e) {
                Bukkit.getPluginManager().disablePlugin(instance);
                e.printStackTrace();
            }
        return null;
    }

    public static JSONObject getLicenseApiResponse(String license_key){
        try {
            //TODO AFTER PUT THIS IN THE VPS, CHANGE THE API REQUEST URL
            URL url = new URL("http://localhost:8000/v1/LICENSE_CHECK/" + license_key);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in .readLine()) != null) {
                response.append(inputLine);
            } in .close();
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response.toString());
            return json;
        } catch(SocketTimeoutException | IllegalArgumentException e){
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(instance);
        } catch (IOException | ParseException e) {
            Bukkit.getPluginManager().disablePlugin(instance);
            e.printStackTrace();
        }
        return null;
    }
}
