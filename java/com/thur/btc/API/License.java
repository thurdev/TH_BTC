package com.thur.btc.API;

import com.thur.btc.Main;
import org.bukkit.Bukkit;
import org.json.simple.JSONObject;

public class License {

    private static final Main instance = Main.getInstance();

    public static Boolean verifyLicense(String license_key){

        try{
            JSONObject obj = Requests.getLicenseApiResponse(license_key);
            String code = "" + obj.get("code");
            if(code.equals(String.valueOf(200))){
                if(obj.get("message").equals("API KEY ACTIVE")){
                    return true;
                }else if(obj.get("message").equals("API KEY DUPED")){
                    return false;
                }else if(obj.get("message").equals("API KEY INACTIVE")){
                    return false;
                }
            }else if(code.equals(String.valueOf(202))){
                return false;
            }else if(code.equals(String.valueOf(404))){
                return false;
            }
        }catch(NullPointerException e){
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(instance);
        }

        return false;
    }
}
