package com.example.nationalparks.util;


public class Util {

    //public static final String PARKS_URL = "https://developer.nps.gov/api/v1/parks?stateCode=AZ&api_key=key";

    public static String getParksUrl(String stateCode){
        return "https://developer.nps.gov/api/v1/parks?stateCode=" + stateCode + "key";
    }
}
