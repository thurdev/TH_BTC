package com.thur.btc.API;

import org.json.simple.JSONObject;

public class BTC {

    public static Double getValue(String currency){
        JSONObject obj = Requests.getApiResponse();
        JSONObject currencyData = (JSONObject) obj.get(currency.toUpperCase());
        return (Double) currencyData.get("last");
    }

    public static String getSymbol(String currency){
        JSONObject obj = Requests.getApiResponse();
        JSONObject currencyData = (JSONObject) obj.get(currency.toUpperCase());
        return (String) currencyData.get("symbol");
    }

    public static Double getSellValue(String currency){
        JSONObject obj = Requests.getApiResponse();
        JSONObject currencyData = (JSONObject) obj.get(currency.toUpperCase());
        return (Double) currencyData.get("buy");
    }

    public static Double getBuyValue(String currency){
        JSONObject obj = Requests.getApiResponse();
        JSONObject currencyData = (JSONObject) obj.get(currency.toUpperCase());
        return (Double) currencyData.get("sell");
    }

}
